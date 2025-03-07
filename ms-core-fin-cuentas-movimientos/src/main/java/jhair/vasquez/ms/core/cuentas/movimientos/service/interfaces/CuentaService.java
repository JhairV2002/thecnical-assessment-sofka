package jhair.vasquez.ms.core.cuentas.movimientos.service.interfaces;


import jhair.vasquez.ms.core.cuentas.movimientos.customExceptions.RecordNotFound;
import jhair.vasquez.ms.core.cuentas.movimientos.entity.Cuenta;

import java.util.List;

public interface CuentaService {
    Cuenta findByNumCuenta(String numCuenta) throws RecordNotFound;
    List<Cuenta> findAll();
    Cuenta save(Cuenta cuenta) throws RecordNotFound;
}
