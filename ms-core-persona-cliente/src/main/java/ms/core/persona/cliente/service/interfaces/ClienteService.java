package ms.core.persona.cliente.service.interfaces;


import ms.core.persona.cliente.base.GenericResponse;
import ms.core.persona.cliente.dtos.req.ClienteReqDTO;
import ms.core.persona.cliente.dtos.res.ClienteResDTO;
import ms.core.persona.cliente.entity.Cliente;

import java.util.List;

public interface ClienteService {
    GenericResponse<ClienteResDTO> getClientInfo();
    GenericResponse<ClienteResDTO> save(ClienteReqDTO cliente) throws Exception;
    GenericResponse<ClienteResDTO> update(ClienteReqDTO cliente) throws Exception;
    GenericResponse<String> delete(Long id);
}
