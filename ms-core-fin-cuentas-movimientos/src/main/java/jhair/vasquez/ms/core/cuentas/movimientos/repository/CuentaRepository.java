package jhair.vasquez.ms.core.cuentas.movimientos.repository;

import jhair.vasquez.ms.core.cuentas.movimientos.entity.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
    Cuenta findCuentaByNumCuenta(String numCuenta);
    Optional<List<Cuenta>> findCuentaByClienteId(Long clienteId);
}
