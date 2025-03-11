package jhair.vasquez.ms.core.cuentas.movimientos.movimientos.application.service;

import jhair.vasquez.ms.core.cuentas.movimientos.movimientos.domain.Movimiento;
import jhair.vasquez.ms.core.cuentas.movimientos.movimientos.infraestructure.repository.MovimientoEntity;
import jhair.vasquez.ms.core.cuentas.movimientos.movimientos.domain.exception.InsufficientFundsException;
import jhair.vasquez.ms.core.cuentas.movimientos.customExceptions.RecordNotFound;

import java.util.List;

public interface MovimientosService {
    Movimiento saveMovimiento(Movimiento movimiento) throws RecordNotFound, InsufficientFundsException;
    List<Movimiento> findMovimientoByCuentaNum(String cuentaNum);
    List<Movimiento> findAll();
}
