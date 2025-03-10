package jhair.vasquez.ms.core.cuentas.movimientos.movimientos.infraestructure.repository;

import jhair.vasquez.ms.core.cuentas.movimientos.movimientos.application.repository.MovimientosRepository;
import jhair.vasquez.ms.core.cuentas.movimientos.movimientos.domain.Movimiento;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@AllArgsConstructor
public class MovimientoRepositoryImpl implements MovimientosRepository {
    private final MovimientosRepositoryJpa movimientoRepositoryJpa;

    @Override
    public Movimiento save(Movimiento movimiento) {
        return movimientoRepositoryJpa.save(movimiento);
    }

    @Override
    public List<Movimiento> findAll() {
        return movimientoRepositoryJpa.findAll();
    }

    @Override
    public List<Movimiento> findAllByCuentaNum(String cuentaNum) {
        return movimientoRepositoryJpa.findAllByCuentaNum(cuentaNum);
    }

    @Override
    public List<Movimiento> findMovimientoByCuentaNumOrderByFechaDesc(String cuentaNum) {
        return movimientoRepositoryJpa.findMovimientoByCuentaNumOrderByFechaDesc(cuentaNum);
    }

    @Override
    public List<Movimiento> findMovimientoByCuentaNumAndFechaBetween(String cuentaNum, Date fechaInicio, Date fechaFin) {
        return movimientoRepositoryJpa.findMovimientoByCuentaNumAndFechaBetween(cuentaNum, fechaInicio, fechaFin);
    }
}
