package jhair.vasquez.ms.core.cuentas.movimientos.movimientos.application.service;

import jhair.vasquez.ms.core.cuentas.movimientos.cuentas.domain.Cuenta;
import jhair.vasquez.ms.core.cuentas.movimientos.movimientos.domain.Movimiento;
import jhair.vasquez.ms.core.cuentas.movimientos.movimientos.domain.exception.InsufficientFundsException;
import jhair.vasquez.ms.core.cuentas.movimientos.customExceptions.RecordNotFound;
import jhair.vasquez.ms.core.cuentas.movimientos.cuentas.infraestructure.repository.CuentaEntity;
import jhair.vasquez.ms.core.cuentas.movimientos.movimientos.application.repository.MovimientosRepository;
import jhair.vasquez.ms.core.cuentas.movimientos.movimientos.infraestructure.repository.MovimientoEntity;
import jhair.vasquez.ms.core.cuentas.movimientos.cuentas.application.service.CuentaService;
import jhair.vasquez.ms.core.cuentas.movimientos.movimientos.domain.utils.MovimientosUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
public class MovimientosServiceImpl implements MovimientosService {
    private final CuentaService cuentaService;
    private final MovimientosRepository movimientosRepository;
    private final MovimientosUtils utils;

    @Override
    public List<Movimiento> findMovimientoByCuentaNum(String cuentaNum) {
        return movimientosRepository.findAllByCuentaNum(cuentaNum);
    }

    @Override
    public List<Movimiento> findAll() {
        return movimientosRepository.findAll();
    }

    @Override
    public Movimiento saveMovimiento(Movimiento movimientoEntity) throws RecordNotFound, InsufficientFundsException {
        String numCuentaOrigen = movimientoEntity.getCuentaNum();
        BigDecimal monto = movimientoEntity.getValor();
        Cuenta cuentaEntity = cuentaService.findByNumCuenta(numCuentaOrigen);
        if (cuentaEntity == null) throw new RecordNotFound("La cuentaEntity no existe");
        List<Movimiento> movimientoEntities = movimientosRepository.findMovimientoByCuentaNumOrderByFechaDesc(numCuentaOrigen);
        BigDecimal saldoBase = movimientoEntities.isEmpty() ? cuentaEntity.getSaldoInicial() : movimientoEntities.getFirst().getSaldo();
        Movimiento newMovimientoEntity = new Movimiento();
        newMovimientoEntity.setCuentaNum(cuentaEntity.getNumCuenta());
        newMovimientoEntity.setTipoMovimiento(utils.getMovementType(monto) + " " +  monto.abs().toString());
        newMovimientoEntity.setValor(monto);
        newMovimientoEntity.setSaldo(utils.calcularSaldo(saldoBase, monto));
        return movimientosRepository.save(newMovimientoEntity);
    }
}
