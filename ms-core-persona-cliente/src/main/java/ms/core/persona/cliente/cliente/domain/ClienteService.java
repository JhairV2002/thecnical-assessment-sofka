package ms.core.persona.cliente.cliente.domain;


import ms.core.persona.cliente.domain.GenericResponse;
import ms.core.persona.cliente.cliente.infraestructure.dto.ClienteReqDTO;
import ms.core.persona.cliente.cliente.infraestructure.dto.ClienteResDTO;

public interface ClienteService {
    GenericResponse<ClienteResDTO> getClientInfo();
    GenericResponse<ClienteResDTO> save(ClienteReqDTO cliente) throws Exception;
    GenericResponse<ClienteResDTO> update(ClienteReqDTO cliente) throws Exception;
    GenericResponse<String> delete(Long id);
}
