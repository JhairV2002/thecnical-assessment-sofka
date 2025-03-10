package jhair.vasquez.ms.core.cuentas.movimientos.movimientos.infraestructure.dtos.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;

@Data
@Builder
@AllArgsConstructor
public class MovimientoResDTO {
    private Date fecha;
    private String tipoMovimiento;
    private BigDecimal valor;
    private BigDecimal saldo;
    private String cuentaNum;
}
