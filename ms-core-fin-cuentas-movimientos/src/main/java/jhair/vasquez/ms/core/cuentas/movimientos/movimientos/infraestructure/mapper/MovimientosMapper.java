package jhair.vasquez.ms.core.cuentas.movimientos.movimientos.infraestructure.mapper;

import jhair.vasquez.ms.core.cuentas.movimientos.movimientos.domain.Movimiento;
import jhair.vasquez.ms.core.cuentas.movimientos.movimientos.infraestructure.dtos.req.MovimientoReqDTO;
import jhair.vasquez.ms.core.cuentas.movimientos.movimientos.infraestructure.dtos.res.MovimientoResDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovimientosMapper {
    Movimiento movimientoReqDTOToMovimiento(MovimientoReqDTO movimientoReqDTO);
    MovimientoResDTO movimientoToMovimientoResDTO(Movimiento movimiento);
    MovimientoReqDTO movimientoToMovimientoReqDTO(Movimiento movimiento);
}
