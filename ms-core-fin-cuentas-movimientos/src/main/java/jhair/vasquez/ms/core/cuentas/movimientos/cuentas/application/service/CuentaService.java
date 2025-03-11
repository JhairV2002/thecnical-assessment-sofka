package jhair.vasquez.ms.core.cuentas.movimientos.cuentas.application.service;


import jhair.vasquez.ms.core.cuentas.movimientos.cuentas.domain.Cuenta;
import jhair.vasquez.ms.core.cuentas.movimientos.customExceptions.RecordNotFound;

import java.util.List;

public interface CuentaService {
    Cuenta findByNumCuenta(String numCuenta) throws RecordNotFound;
    List<Cuenta> findAll();
    Cuenta save(Cuenta cuentaEntity) throws RecordNotFound;
}
