package jhair.vasquez.ms.core.cuentas.movimientos.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.sql.Date;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "movimientos", schema = "movimientos")
public class Movimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreationTimestamp
    private Date fecha;
    private String tipoMovimiento;
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal saldo;
    // foreign key with Cuenta
    @Column(nullable = false)
    private String cuentaNum;
}
