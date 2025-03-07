package jhair.vasquez.ms.core.cuentas.movimientos.repository;

import jhair.vasquez.ms.core.cuentas.movimientos.entity.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface MovimientosRepository extends JpaRepository<Movimiento, Long> {
    List<Movimiento> findAll();
    List<Movimiento> findAllByCuentaNum(String cuentaNum);
    List<Movimiento> findMovimientoByCuentaNumOrderByFechaDesc(String cuentaNum);
    List<Movimiento> findMovimientoByCuentaNumAndFechaBetween(String cuentaNum, Date fechaInicio, Date fechaFin);
}
