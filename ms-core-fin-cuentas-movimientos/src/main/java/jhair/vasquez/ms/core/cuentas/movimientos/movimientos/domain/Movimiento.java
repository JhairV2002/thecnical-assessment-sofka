package jhair.vasquez.ms.core.cuentas.movimientos.movimientos.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Movimiento {
    private Long id;
    private Date fecha;
    private String tipoMovimiento;
    private BigDecimal valor;
    private BigDecimal saldo;
    private String cuentaNum;
}
