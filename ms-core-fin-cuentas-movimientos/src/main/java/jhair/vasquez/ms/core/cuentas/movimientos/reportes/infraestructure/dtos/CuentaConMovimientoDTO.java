package jhair.vasquez.ms.core.cuentas.movimientos.reportes.infraestructure.dtos;

import jhair.vasquez.ms.core.cuentas.movimientos.cuentas.domain.Cuenta;
import jhair.vasquez.ms.core.cuentas.movimientos.movimientos.domain.Movimiento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CuentaConMovimientoDTO implements Serializable {
    private Cuenta cuenta;
    private BigDecimal saldoActual; // Calculado desde el último movimiento
    private List<Movimiento> movimientos; // Movimientos en el rango de fechas
}
