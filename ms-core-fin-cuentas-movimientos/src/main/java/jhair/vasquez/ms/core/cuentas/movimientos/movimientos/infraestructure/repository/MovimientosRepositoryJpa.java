package jhair.vasquez.ms.core.cuentas.movimientos.movimientos.infraestructure.repository;

import jhair.vasquez.ms.core.cuentas.movimientos.movimientos.domain.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface MovimientosRepositoryJpa extends JpaRepository<Movimiento, Long> {
    List<Movimiento> findAll();
    List<Movimiento> findAllByCuentaNum(String cuentaNum);
    List<Movimiento> findMovimientoByCuentaNumOrderByFechaDesc(String cuentaNum);
    List<Movimiento> findMovimientoByCuentaNumAndFechaBetween(String cuentaNum, Date fechaInicio, Date fechaFin);
}
