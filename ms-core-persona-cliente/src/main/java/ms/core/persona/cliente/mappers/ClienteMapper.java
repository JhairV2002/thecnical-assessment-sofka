package ms.core.persona.cliente.mappers;

import db.repositorio.financiero.dtos.ClienteDTO;
import db.repositorio.financiero.entity.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ClienteMapper {
    ClienteMapper clienteMapper = Mappers.getMapper(ClienteMapper.class);

    ClienteDTO clienteToClienteDTO(Cliente cliente);
    List<ClienteDTO> clienteListToClienteDTO(List<Cliente> clienteList);
    Cliente clienteDTOToCliente(ClienteDTO clienteDTO);
}
