package ms.core.persona.cliente.cliente.application;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms.core.persona.cliente.domain.GenericResponse;
import ms.core.persona.cliente.cliente.domain.ClienteService;
import ms.core.persona.cliente.application.exceptions.InvalidFieldException;
import ms.core.persona.cliente.domain.customExceptions.RecordAlreadyExistsException;
import ms.core.persona.cliente.domain.customExceptions.RecordNotFoundException;
import ms.core.persona.cliente.cliente.infraestructure.dto.ClienteReqDTO;
import ms.core.persona.cliente.cliente.infraestructure.dto.ClienteResDTO;
import ms.core.persona.cliente.cliente.domain.Cliente;
import ms.core.persona.cliente.cliente.infraestructure.repository.ClienteRepositoryImpl;
import ms.core.persona.cliente.cliente.application.mappers.ClienteMapper;

import ms.core.persona.cliente.utils.impl.FiledsUtils;
import ms.core.persona.cliente.config.application.JWTUtilsImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import static ms.core.persona.cliente.utils.impl.FiledsUtils.updateFieldIfPresent;

@Service
@AllArgsConstructor
@Slf4j
public class ClienteServiceImpl implements ClienteService {
    private final ClienteRepositoryImpl clienteRepository;
    private final ClienteMapper clienteMapper;
    private final JWTUtilsImpl jwtUtils;
    private final FiledsUtils filedUtils;

    /**
     * Obtiene la información del cliente autenticado basado en su identificación.
     *
     * @return GenericResponse con el ClienteDTO del cliente autenticado.
     * @throws RecordNotFoundException si no se encuentra un cliente con la identificación del usuario autenticado.
     */
    @Override
    public GenericResponse<ClienteResDTO> getClientInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String identificacion = authentication.getName();

        log.info("Consultando información del cliente con identificación: {}", identificacion);

        Cliente cliente = clienteRepository.findClienteByIdentificacion(identificacion)
                .orElseThrow(() -> {
                    String errorMsg = "No se encontró el cliente con la identificación: " + identificacion;
                    log.error(errorMsg);
                    return new RecordNotFoundException(errorMsg);
                });

        log.info("Cliente consultado exitosamente: {}", cliente.getIdentificacion());


        return GenericResponse.<ClienteResDTO>builder()
                .status(HttpStatusCode.valueOf(HttpStatus.OK.value()))
                .message("Success")
                .payload(clienteMapper.clienteToClienteResDTO(cliente))
                .build();
    }

    /**
     * Guarda un nuevo cliente en la base de datos.
     *
     * @param cliente El cliente a guardar.
     * @return GenericResponse con el ClienteDTO del cliente guardado.
     * @throws RecordAlreadyExistsException si ya existe un cliente con la misma identificación.
     * @throws InvalidFieldException        si la identificación no cumple con las validaciones (longitud o formato numérico).
     * @throws Exception                    si ocurre un error inesperado durante el guardado.
     */
    @Override
    public GenericResponse<ClienteResDTO> save(ClienteReqDTO cliente) {
        String identificacion = cliente.getIdentificacion();
        log.info("Intentando guardar cliente con identificación: {}", identificacion);

        validateIdentification(identificacion);

        return clienteRepository.findClienteByIdentificacion(identificacion)
                .map(this::handleExistingClient)
                .orElseGet(() -> saveNewClient(cliente));
    }

    /**
     * Maneja el caso en el que ya existe un cliente con la misma identificación.
     * Lanza una excepción para indicar que el cliente ya existe.
     *
     * @param cliente El cliente existente en la base de datos.
     * @return Este método no retorna nada, siempre lanza una excepción.
     * @throws RecordAlreadyExistsException si ya existe un cliente con la misma identificación.
     */
    private GenericResponse<ClienteResDTO> handleExistingClient(Cliente cliente) {
        String errorMsg = "Ya existe un cliente con la identificación: " + cliente.getIdentificacion();
        log.warn(errorMsg);
        throw new RecordAlreadyExistsException(errorMsg);
    }
    /**
     * Valida la identificación del cliente, asegurándose de que cumpla con las reglas de longitud y formato numérico.
     *
     * @param identificacion La identificación del cliente a validar.
     * @throws InvalidFieldException si la identificación no cumple con las validaciones de longitud (máximo 10 caracteres)
     *                              o formato (debe ser un número válido).
     */
    private void validateIdentification(String identificacion) {
        // Validación de longitud
        if (identificacion.length() > 10) {
            String errorMsg = "La identificación debe tener 10 números";
            log.warn(errorMsg);
            throw new InvalidFieldException(errorMsg);
        }

        // Validación de formato numérico
        try {
            Long.parseLong(identificacion);
        } catch (NumberFormatException e) {
            String errorMsg = "La identificación debe ser un número";
            log.warn(errorMsg);
            throw new InvalidFieldException(errorMsg);
        }
    }

    /**
     * Guarda un nuevo cliente en la base de datos y construye la respuesta correspondiente.
     *
     * @param clienteDTO El objeto ClienteReqDTO con los datos del cliente a guardar.
     * @return GenericResponse con el ClienteResDTO del cliente guardado en caso de éxito,
     *         o un mensaje de error en caso de fallo.
     */
    private GenericResponse<ClienteResDTO> saveNewClient(ClienteReqDTO clienteDTO) {
        try {
            // Guardamos el cliente
            Cliente clienteToSave = clienteMapper.clienteReqDTOToCliente(clienteDTO);
            Cliente savedCliente = clienteRepository.save(clienteToSave);
            log.info("Cliente guardado exitosamente: {}", savedCliente.getIdentificacion());

            return GenericResponse.<ClienteResDTO>builder()
                    .status(HttpStatusCode.valueOf(HttpStatus.OK.value()))
                    .message("Success")
                    .payload(clienteMapper.clienteToClienteResDTO(savedCliente))
                    .build();
        } catch (Exception e) {
            log.error("Error al guardar el cliente: {}", e.getMessage());
            return GenericResponse.<ClienteResDTO>builder()
                    .status(HttpStatusCode.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                    .message("Error al guardar el cliente")
                    .payload(null)
                    .build();
        }
    }

    /**
     * Actualiza los datos del cliente autenticado, modificando solo los campos proporcionados en el DTO.
     *
     * @param clienteDTO DTO con los datos a actualizar.
     * @return GenericResponse con el ClienteDTO del cliente actualizado.
     * @throws RecordNotFoundException si no se encuentra un cliente con la identificación del usuario autenticado.
     * @throws Exception               si ocurre un error inesperado durante la actualización.
     */
    @Override
    public GenericResponse<ClienteResDTO> update(ClienteReqDTO clienteDTO) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String identificacion = authentication.getName();

        log.info("Intentando actualizar cliente con identificación: {}", identificacion);

        Cliente clientToUpdate = clienteRepository.findClienteByIdentificacion(identificacion)
                .orElseThrow(() -> {
                    String errorMsg = "No se encontró el cliente con la identificación: " + identificacion;
                    log.error(errorMsg);
                    return new RecordNotFoundException(errorMsg);
                });

        // Actualizar solo los campos enviados en el DTO
        updateFieldIfPresent(clientToUpdate::setNombres, clienteDTO.getNombres());
        updateFieldIfPresent(clientToUpdate::setGenero, clienteDTO.getGenero());
        updateFieldIfPresent(clientToUpdate::setEdad, clienteDTO.getEdad());
        updateFieldIfPresent(clientToUpdate::setIdentificacion, clienteDTO.getIdentificacion());
        updateFieldIfPresent(clientToUpdate::setDireccion, clienteDTO.getDireccion());
        updateFieldIfPresent(clientToUpdate::setTelefono, clienteDTO.getTelefono());

        Cliente updatedCliente = clienteRepository.save(clientToUpdate);
        log.info("Cliente actualizado exitosamente: {}", updatedCliente.getIdentificacion());

        ClienteResDTO updatedClienteDTO = clienteMapper.clienteToClienteResDTO(updatedCliente);
        return GenericResponse.<ClienteResDTO>builder()
                .status(HttpStatusCode.valueOf(HttpStatus.OK.value()))
                .message("Success")
                .payload(updatedClienteDTO)
                .build();
    }

    /**
     * Realiza una eliminación lógica del cliente especificado por su ID, cambiando su estado a falso.
     *
     * @param id ID del cliente a eliminar lógicamente.
     * @return GenericResponse con un mensaje indicando el éxito de la operación.
     * @throws RecordNotFoundException si no se encuentra un cliente con el ID proporcionado.
     */
    @Override
    public GenericResponse<String> delete(Long id) {
        log.info("Intentando eliminar lógicamente cliente con ID: {}", id);

        Cliente clientToDelete = clienteRepository.findById(id)
                .orElseThrow(() -> {
                    String errorMsg = "No se encontró el cliente con el id: " + id;
                    log.error(errorMsg);
                    return new RecordNotFoundException(errorMsg);
                });

        clientToDelete.setEstado(Boolean.FALSE);
        clienteRepository.save(clientToDelete);

        log.info("Cliente eliminado lógicamente: {}", id);

        return GenericResponse.<String>builder()
                .status(HttpStatusCode.valueOf(HttpStatus.OK.value()))
                .message("Success")
                .payload("Cliente eliminado correctamente")
                .build();
    }
}
