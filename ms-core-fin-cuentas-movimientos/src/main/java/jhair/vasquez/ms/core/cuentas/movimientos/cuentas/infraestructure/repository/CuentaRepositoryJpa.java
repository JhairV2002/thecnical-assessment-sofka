package jhair.vasquez.ms.core.cuentas.movimientos.cuentas.infraestructure.repository;

import jhair.vasquez.ms.core.cuentas.movimientos.cuentas.domain.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CuentaRepositoryJpa extends JpaRepository<Cuenta, Long> {
    Optional<Cuenta> findCuentaByNumCuenta(String numCuenta);
    Optional<List<Cuenta>> findCuentaByClienteId(Long clienteId);
}
