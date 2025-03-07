package jhair.vasquez.ms.core.cuentas.movimientos.service.interfaces;

import jhair.vasquez.ms.core.cuentas.movimientos.customExceptions.InsufficientFundsException;
import jhair.vasquez.ms.core.cuentas.movimientos.customExceptions.RecordNotFound;
import jhair.vasquez.ms.core.cuentas.movimientos.entity.Movimiento;

import java.math.BigDecimal;
import java.util.List;

public interface MovimientosService {
    Movimiento saveMovimiento(Movimiento movimiento) throws RecordNotFound, InsufficientFundsException;
    List<Movimiento> findMovimientoByCuentaNum(String cuentaNum);
    List<Movimiento> findAll();
    Movimiento findById(Long id) throws RecordNotFound;
}
