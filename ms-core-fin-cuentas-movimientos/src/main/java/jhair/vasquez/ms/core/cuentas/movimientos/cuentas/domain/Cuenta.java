package jhair.vasquez.ms.core.cuentas.movimientos.cuentas.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cuenta {
    private String numCuenta;
    private CuentaTipo tipoCuenta;
    private BigDecimal saldoInicial;
    private boolean estado;
    private Long clienteId;
}
