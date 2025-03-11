package jhair.vasquez.ms.core.cuentas.movimientos.movimientos.domain.utils;

import jhair.vasquez.ms.core.cuentas.movimientos.movimientos.domain.exception.InsufficientFundsException;

import java.math.BigDecimal;

public interface MovimientosUtils {
    BigDecimal calcularSaldo(BigDecimal saldoInicial, BigDecimal monto) throws InsufficientFundsException;
    String getMovementType(BigDecimal monto);
}
