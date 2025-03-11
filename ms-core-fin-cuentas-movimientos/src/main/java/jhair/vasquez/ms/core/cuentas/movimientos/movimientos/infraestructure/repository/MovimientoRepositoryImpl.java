package jhair.vasquez.ms.core.cuentas.movimientos.movimientos.infraestructure.repository;

import jhair.vasquez.ms.core.cuentas.movimientos.movimientos.application.repository.MovimientosRepository;
import jhair.vasquez.ms.core.cuentas.movimientos.movimientos.domain.Movimiento;
import jhair.vasquez.ms.core.cuentas.movimientos.movimientos.infraestructure.mapper.MovimientosMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class MovimientoRepositoryImpl implements MovimientosRepository {
    private final MovimientosRepositoryJpa movimientoRepositoryJpa;
    private final MovimientosMapper movimientosMapper;


    @Override
    public Movimiento save(Movimiento movimientoEntity) {
        return  movimientosMapper.movimientoEntityToMovimiento(movimientoRepositoryJpa.save(
                movimientosMapper.movimientoToMovimientoEntity(movimientoEntity)
        ));
    }

    @Override
    public List<Movimiento> findAll() {
        return movimientoRepositoryJpa.findAll()
                .stream().map(movimientosMapper::movimientoEntityToMovimiento).collect(Collectors.toList());
    }

    @Override
    public List<Movimiento> findAllByCuentaNum(String cuentaNum) {
        return movimientoRepositoryJpa.findAllByCuentaNum(cuentaNum)
                .stream().map(movimientosMapper::movimientoEntityToMovimiento).collect(Collectors.toList());
    }

    @Override
    public List<Movimiento> findMovimientoByCuentaNumOrderByFechaDesc(String cuentaNum) {
        return movimientoRepositoryJpa.findMovimientoByCuentaNumOrderByFechaDesc(cuentaNum)
                .stream().map(movimientosMapper::movimientoEntityToMovimiento).collect(Collectors.toList());
    }

    @Override
    public List<Movimiento> findMovimientoByCuentaNumAndFechaBetween(String cuentaNum, Date fechaInicio, Date fechaFin) {
        return movimientoRepositoryJpa.findMovimientoByCuentaNumAndFechaBetween(cuentaNum, fechaInicio, fechaFin)
                .stream().map(movimientosMapper::movimientoEntityToMovimiento).collect(Collectors.toList());
    }
}
