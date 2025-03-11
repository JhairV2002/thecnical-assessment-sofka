package ms.core.persona.cliente.cliente.infraestructure.repository;

import ms.core.persona.cliente.cliente.domain.Cliente;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepositoryJpa extends CrudRepository<Cliente, Long> {
    Optional<Cliente> findClienteByIdentificacion(String identificacion);
}