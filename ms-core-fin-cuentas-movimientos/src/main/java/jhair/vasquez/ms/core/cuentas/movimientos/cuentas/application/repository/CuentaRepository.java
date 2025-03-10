package jhair.vasquez.ms.core.cuentas.movimientos.cuentas.application.repository;

import jhair.vasquez.ms.core.cuentas.movimientos.cuentas.domain.Cuenta;

import java.util.List;
import java.util.Optional;

public interface CuentaRepository {
    Optional<Cuenta> findCuentaByNumCuenta(String numCuenta);
    List<Cuenta> findAll();
    Cuenta save(Cuenta cuenta);
    Optional<List<Cuenta>> findCuentaByClienteId(Long clienteId);
}
