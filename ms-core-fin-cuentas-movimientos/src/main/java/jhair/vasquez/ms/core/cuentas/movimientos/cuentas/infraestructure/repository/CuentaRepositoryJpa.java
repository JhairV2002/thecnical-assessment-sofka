package jhair.vasquez.ms.core.cuentas.movimientos.cuentas.infraestructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CuentaRepositoryJpa extends JpaRepository<CuentaEntity, Long> {
    Optional<CuentaEntity> findCuentaByNumCuenta(String numCuenta);
    Optional<List<CuentaEntity>> findCuentaByClienteId(Long clienteId);
}
