package ms.core.persona.cliente.service.interfaces;

import db.repositorio.financiero.dtos.ClienteDTO;
import db.repositorio.financiero.entity.Cliente;
import ms.core.persona.cliente.base.GenericResponse;
import ms.core.persona.cliente.customExceptions.RecordNotFoundException;

import java.util.List;

public interface ClienteService {
    GenericResponse<ClienteDTO> getClientInfo();
    GenericResponse<ClienteDTO> save(Cliente cliente) throws Exception;
    GenericResponse<ClienteDTO> update(ClienteDTO cliente) throws Exception;
    GenericResponse<String> delete(Long id);
}
