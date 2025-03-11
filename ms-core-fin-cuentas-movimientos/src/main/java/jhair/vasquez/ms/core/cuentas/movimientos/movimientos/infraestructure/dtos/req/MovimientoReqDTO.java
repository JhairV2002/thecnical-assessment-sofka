package jhair.vasquez.ms.core.cuentas.movimientos.movimientos.infraestructure.dtos.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class MovimientoReqDTO {
    private String cuentaNum;
    private String valor;
}
