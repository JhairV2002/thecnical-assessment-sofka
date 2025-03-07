package ms.core.persona.cliente.repository;

import ms.core.persona.cliente.entity.Cliente;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends CrudRepository<Cliente, Long> {
    List<Cliente> findAll();
    List<Cliente> findByEstado(boolean estado);
    Optional<Cliente> findClienteByIdentificacion(String identificacion);
}