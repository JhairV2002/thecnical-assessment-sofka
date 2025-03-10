package ms.core.persona.cliente.cliente.infraestructure.repository;

import lombok.RequiredArgsConstructor;
import ms.core.persona.cliente.cliente.domain.ClienteRepository;
import ms.core.persona.cliente.cliente.domain.Cliente;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ClienteRepositoryImpl implements ClienteRepository{
    private final ClienteRepositoryJpa clienteRepositoryJpa;

    @Override
    public Optional<Cliente> findClienteByIdentificacion(String identificacion) {
        return clienteRepositoryJpa.findClienteByIdentificacion(identificacion);
    }

    @Override
    public Optional<Cliente> findById(Long id) {
        return clienteRepositoryJpa.findById(id);
    }

    @Override
    public Cliente save(Cliente cliente) {
        return clienteRepositoryJpa.save(cliente);
    }
}
