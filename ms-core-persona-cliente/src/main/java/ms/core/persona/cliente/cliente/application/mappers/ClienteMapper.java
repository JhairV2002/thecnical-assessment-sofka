package ms.core.persona.cliente.cliente.application.mappers;

import ms.core.persona.cliente.cliente.infraestructure.dto.ClienteReqDTO;
import ms.core.persona.cliente.cliente.infraestructure.dto.ClienteResDTO;
import ms.core.persona.cliente.cliente.domain.Cliente;
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
