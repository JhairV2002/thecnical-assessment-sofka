package jhair.vasquez.ms.core.cuentas.movimientos.movimientos.application.repository;

import jhair.vasquez.ms.core.cuentas.movimientos.movimientos.domain.Movimiento;

import java.util.Date;
import java.util.List;

public interface MovimientosRepository {
    Movimiento save(Movimiento movimientoEntity);
    List<Movimiento> findAll();
    List<Movimiento> findAllByCuentaNum(String cuentaNum);
    List<Movimiento> findMovimientoByCuentaNumOrderByFechaDesc(String cuentaNum);
    List<Movimiento> findMovimientoByCuentaNumAndFechaBetween(String cuentaNum, Date fechaInicio, Date fechaFin);
}
