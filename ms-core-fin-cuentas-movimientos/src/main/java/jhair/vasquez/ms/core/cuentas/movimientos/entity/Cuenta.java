package jhair.vasquez.ms.core.cuentas.movimientos.entity;

import jakarta.persistence.*;
import jhair.vasquez.ms.core.cuentas.movimientos.enums.CuentaTipo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cuentas", schema = "cuenta")
public class Cuenta {
    @Id
    @Column(nullable = false, length = 10)
    private String numCuenta;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CuentaTipo tipoCuenta;
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal saldoInicial;
    @Column(nullable = false)
    private boolean estado;
    @Column(nullable = false)
    private Long clienteId;
}
