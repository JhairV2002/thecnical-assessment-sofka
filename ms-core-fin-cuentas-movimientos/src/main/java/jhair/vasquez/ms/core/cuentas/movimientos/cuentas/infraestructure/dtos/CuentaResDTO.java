package jhair.vasquez.ms.core.cuentas.movimientos.cuentas.infraestructure.dtos;

import jhair.vasquez.ms.core.cuentas.movimientos.cuentas.domain.CuentaTipo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
public class CuentaResDTO {
    private String numCuenta;
    private CuentaTipo tipoCuenta;
    private BigDecimal saldoInicial;
}
