package jhair.vasquez.ms.core.cuentas.movimientos.movimientos.infraestructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface MovimientosRepositoryJpa extends JpaRepository<MovimientoEntity, Long> {
    List<MovimientoEntity> findAll();
    List<MovimientoEntity> findAllByCuentaNum(String cuentaNum);
    List<MovimientoEntity> findMovimientoByCuentaNumOrderByFechaDesc(String cuentaNum);
    List<MovimientoEntity> findMovimientoByCuentaNumAndFechaBetween(String cuentaNum, Date fechaInicio, Date fechaFin);
}
