package jhair.vasquez.ms.core.cuentas.movimientos.reportes.infraestructure.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReporteDTO implements Serializable {
    private Long clienteId;
    private List<CuentaConMovimientoDTO> cuentas;
}
