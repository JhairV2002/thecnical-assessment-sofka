package ms.core.persona.cliente.cliente.domain;

import java.util.Optional;

public interface ClienteRepository {
    Optional<Cliente> findClienteByIdentificacion(String identificacion);
    Optional<Cliente> findById(Long id);
    Cliente save(Cliente cliente);
}
