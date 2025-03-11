package jhair.vasquez.ms.core.cuentas.movimientos.cuentas.infraestructure.repository;

import jakarta.persistence.*;
import jhair.vasquez.ms.core.cuentas.movimientos.cuentas.domain.Cuenta;
import jhair.vasquez.ms.core.cuentas.movimientos.cuentas.domain.CuentaTipo;
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
public class CuentaEntity {
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

    public static CuentaEntity fromDomain(Cuenta cuenta) {
        return CuentaEntity.builder()
                .numCuenta(cuenta.getNumCuenta())
                .tipoCuenta(cuenta.getTipoCuenta())
                .saldoInicial(cuenta.getSaldoInicial())
                .estado(cuenta.isEstado())
                .clienteId(cuenta.getClienteId())
                .build();
    }

    public Cuenta toDomain() {
        return Cuenta.builder()
                .numCuenta(numCuenta)
                .tipoCuenta(tipoCuenta)
                .saldoInicial(saldoInicial)
                .estado(estado)
                .clienteId(clienteId)
                .build();
    }
}
