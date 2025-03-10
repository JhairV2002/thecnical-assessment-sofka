package jhair.vasquez.ms.core.cuentas.movimientos.movimientos.application.service;

import jhair.vasquez.ms.core.cuentas.movimientos.movimientos.domain.exception.InsufficientFundsException;
import jhair.vasquez.ms.core.cuentas.movimientos.customExceptions.RecordNotFound;
import jhair.vasquez.ms.core.cuentas.movimientos.cuentas.domain.Cuenta;
import jhair.vasquez.ms.core.cuentas.movimientos.movimientos.application.repository.MovimientosRepository;
import jhair.vasquez.ms.core.cuentas.movimientos.movimientos.domain.Movimiento;
import jhair.vasquez.ms.core.cuentas.movimientos.cuentas.application.service.CuentaService;
import jhair.vasquez.ms.core.cuentas.movimientos.movimientos.domain.utils.MovimientosUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
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
    public Movimiento saveMovimiento(Movimiento movimiento) throws RecordNotFound, InsufficientFundsException {
        String numCuentaOrigen = movimiento.getCuentaNum();
        BigDecimal monto = movimiento.getValor();
        // validate if the account exists
        Cuenta cuenta = cuentaService.findByNumCuenta(numCuentaOrigen);
        if (cuenta == null) throw new RecordNotFound("La cuenta no existe");
        // get all movements from the account
        List<Movimiento> movimientos = movimientosRepository.findMovimientoByCuentaNumOrderByFechaDesc(numCuentaOrigen);
        BigDecimal saldoBase = movimientos.isEmpty() ? cuenta.getSaldoInicial() : movimientos.getFirst().getSaldo();
        // if there are no movements, create the first movement
        Movimiento newMovimiento = new Movimiento();
        newMovimiento.setCuentaNum(cuenta.getNumCuenta());
        newMovimiento.setTipoMovimiento(utils.getMovementType(monto) + " " +  monto.abs().toString());
        newMovimiento.setValor(monto);
        newMovimiento.setSaldo(utils.calcularSaldo(saldoBase, monto));
        return movimientosRepository.save(newMovimiento);
    }
}
