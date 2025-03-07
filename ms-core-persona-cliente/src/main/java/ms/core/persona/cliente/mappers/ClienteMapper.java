package ms.core.persona.cliente.mappers;

import ms.core.persona.cliente.dtos.req.ClienteReqDTO;
import ms.core.persona.cliente.dtos.res.ClienteResDTO;
import ms.core.persona.cliente.entity.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClienteMapper {
    ClienteMapper clienteMapper = Mappers.getMapper(ClienteMapper.class);

    ClienteResDTO clienteToClienteResDTO(Cliente cliente);
    List<ClienteResDTO> clienteListToClienteResDTO(List<Cliente> clienteList);
    Cliente clienteReqDTOToCliente(ClienteReqDTO clienteDTO);
}
