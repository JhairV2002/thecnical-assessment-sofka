package ms.core.persona.cliente.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import ms.core.persona.cliente.base.GenericResponse;
import ms.core.persona.cliente.controller.ClienteController;
import ms.core.persona.cliente.customExceptions.InvalidFieldException;
import ms.core.persona.cliente.customExceptions.RecordAlreadyExistsException;
import ms.core.persona.cliente.customExceptions.RecordNotFoundException;
import ms.core.persona.cliente.dtos.req.ClienteReqDTO;
import ms.core.persona.cliente.dtos.res.ClienteResDTO;
import ms.core.persona.cliente.entity.Cliente;
import ms.core.persona.cliente.enums.Genero;
import ms.core.persona.cliente.service.impl.ClienteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.hamcrest.Matchers.is;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ClienteControllerTests {

    @Mock
    private ClienteServiceImpl clienteService;

    @InjectMocks
    private ClienteController clienteController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private Cliente cliente;
    private ClienteResDTO clienteDTO;
    private GenericResponse<ClienteResDTO> successResponse;
    private GenericResponse<String> deleteSuccessResponse;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(clienteController).build();
        objectMapper = new ObjectMapper();

        // Configuración de objetos de prueba
        cliente = new Cliente();
        cliente.setClienteId(1L);
        cliente.setIdentificacion("1717171712");
        cliente.setNombres("Juan Pérez");
        cliente.setGenero(Genero.MASCULINO);
        cliente.setEdad(30);
        cliente.setDireccion("Calle Falsa 123");
        cliente.setTelefono("1234567890");
        cliente.setContrasenia("securePass123");
        cliente.setEstado(true);

        clienteDTO = new ClienteResDTO();
        clienteDTO.setIdentificacion("1717171712");
        clienteDTO.setNombres("Juan Pérez");
        clienteDTO.setGenero(Genero.MASCULINO);
        clienteDTO.setEdad(30);
        clienteDTO.setDireccion("Calle Falsa 123");
        clienteDTO.setTelefono("1234567890");

        successResponse = GenericResponse.<ClienteResDTO>builder()
                .status(HttpStatusCode.valueOf(HttpStatus.OK.value()))
                .message("Success")
                .payload(clienteDTO)
                .build();

        deleteSuccessResponse = GenericResponse.<String>builder()
                .status(HttpStatusCode.valueOf(HttpStatus.OK.value()))
                .message("Success")
                .payload("Cliente eliminado correctamente")
                .build();
    }

    @Test
    void save_Success_ReturnsSavedCliente() throws Exception {
        // Given
        when(clienteService.save(any(ClienteReqDTO.class))).thenReturn(successResponse);

        // When & Then
        mockMvc.perform(post("/clientes/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cliente)))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.status", is("OK")))
                .andExpect(jsonPath("$.message", is("Success")))
                .andExpect(jsonPath("$.payload.identificacion", is("1717171712")))
                .andExpect(jsonPath("$.payload.nombres", is("Juan Pérez")));

        verify(clienteService, times(1)).save(any(ClienteReqDTO.class));
    }

    @Test
    void save_ClienteAlreadyExists_ThrowsRecordAlreadyExistsException() throws Exception {
        // Given
        when(clienteService.save(any(ClienteReqDTO.class)))
                .thenThrow(new RecordAlreadyExistsException("Ya existe un cliente con la identificación: 1717171712"));

        // When & Then
        mockMvc.perform(post("/clientes/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cliente)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status", is(HttpStatus.CONFLICT.value())))
                .andExpect(jsonPath("$.message", is("Ya existe un cliente con la identificación: 1717171712")));

        verify(clienteService, times(1)).save(any(ClienteReqDTO.class));
    }

    @Test
    void save_InvalidIdentificacionLength_ThrowsInvalidFieldException() throws Exception {
        // Given
        cliente.setIdentificacion("12345678901"); // 11 caracteres
        when(clienteService.save(any(ClienteReqDTO.class)))
                .thenThrow(new InvalidFieldException("La identificación debe tener 10 números"));

        // When & Then
        mockMvc.perform(post("/clientes/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cliente)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.message", is("La identificación debe tener 10 números")));

        verify(clienteService, times(1)).save(any(ClienteReqDTO.class));
    }

    @Test
    void save_NonNumericIdentificacion_ThrowsInvalidFieldException() throws Exception {
        // Given
        cliente.setIdentificacion("not_a_number");
        when(clienteService.save(any(ClienteReqDTO.class)))
                .thenThrow(new InvalidFieldException("La identificación debe ser un número"));

        // When & Then
        mockMvc.perform(post("/clientes/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cliente)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.message", is("La identificación debe ser un número")));

        verify(clienteService, times(1)).save(any(ClienteReqDTO.class));
    }

    @Test
    void findById_Success_ReturnsClientInfo() throws Exception {
        // Given
        when(clienteService.getClientInfo()).thenReturn(successResponse);

        // When & Then
        mockMvc.perform(get("/clientes/userInfo")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(HttpStatus.OK.value())))
                .andExpect(jsonPath("$.message", is("Success")))
                .andExpect(jsonPath("$.payload.identificacion", is("1717171712")))
                .andExpect(jsonPath("$.payload.nombres", is("Juan Pérez")));

        verify(clienteService, times(1)).getClientInfo();
    }

    @Test
    void findById_ClientNotFound_ThrowsRecordNotFoundException() throws Exception {
        // Given
        when(clienteService.getClientInfo())
                .thenThrow(new RecordNotFoundException("No se encontró el cliente con la identificación: 1717171712"));

        // When & Then
        mockMvc.perform(get("/clientes/userInfo")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(HttpStatus.NOT_FOUND.value())))
                .andExpect(jsonPath("$.message", is("No se encontró el cliente con la identificación: 1717171712")));

        verify(clienteService, times(1)).getClientInfo();
    }

    @Test
    void update_Success_ReturnsUpdatedClient() throws Exception {
        // Given
        when(clienteService.update(any(ClienteReqDTO.class))).thenReturn(successResponse);

        // When & Then
        mockMvc.perform(put("/clientes/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clienteDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(HttpStatus.OK.value())))
                .andExpect(jsonPath("$.message", is("Success")))
                .andExpect(jsonPath("$.payload.identificacion", is("1717171712")));

        verify(clienteService, times(1)).update(any(ClienteReqDTO.class));
    }

    @Test
    void update_ClientNotFound_ThrowsRecordNotFoundException() throws Exception {
        // Given
        when(clienteService.update(any(ClienteReqDTO.class)))
                .thenThrow(new RecordNotFoundException("No se encontró el cliente con la identificación: 1717171712"));

        // When & Then
        mockMvc.perform(put("/clientes/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clienteDTO)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(HttpStatus.NOT_FOUND.value())))
                .andExpect(jsonPath("$.message", is("No se encontró el cliente con la identificación: 1717171712")));

        verify(clienteService, times(1)).update(any(ClienteReqDTO.class));
    }

    @Test
    void delete_Success_ReturnsVoid() throws Exception {
        // Given
        doNothing().when(clienteService).delete(eq(1L));

        // When & Then
        mockMvc.perform(delete("/clientes/1"))
                .andExpect(status().isOk());

        verify(clienteService, times(1)).delete(eq(1L));
    }

    @Test
    void delete_ClientNotFound_ThrowsRecordNotFoundException() throws Exception {
        // Given
        doThrow(new RecordNotFoundException("No se encontró el cliente con el id: 1"))
                .when(clienteService).delete(eq(1L));

        // When & Then
        mockMvc.perform(delete("/clientes/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(HttpStatus.NOT_FOUND.value())))
                .andExpect(jsonPath("$.message", is("No se encontró el cliente con el id: 1")));

        verify(clienteService, times(1)).delete(eq(1L));
    }
}
