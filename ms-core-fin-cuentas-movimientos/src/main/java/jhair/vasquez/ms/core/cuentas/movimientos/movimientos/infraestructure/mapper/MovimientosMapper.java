package jhair.vasquez.ms.core.cuentas.movimientos.movimientos.infraestructure.mapper;

import jhair.vasquez.ms.core.cuentas.movimientos.movimientos.domain.Movimiento;
import jhair.vasquez.ms.core.cuentas.movimientos.movimientos.infraestructure.repository.MovimientoEntity;
import jhair.vasquez.ms.core.cuentas.movimientos.movimientos.infraestructure.dtos.req.MovimientoReqDTO;
import jhair.vasquez.ms.core.cuentas.movimientos.movimientos.infraestructure.dtos.res.MovimientoResDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MovimientosMapper {
    @Mapping(source = "cuentaNum", target = "cuentaNum")
    @Mapping(source = "valor", target = "valor")
    Movimiento movimientoReqDTOToMovimientoEntity(MovimientoReqDTO movimientoReqDTO);
    MovimientoResDTO movimientoToMovimientoResDTO(Movimiento movimientoEntity);
    MovimientoReqDTO movimientoToMovimientoReqDTO(Movimiento movimientoEntity);

    Movimiento movimientoEntityToMovimiento(MovimientoEntity movimientoEntity);
    MovimientoEntity movimientoToMovimientoEntity(Movimiento movimiento);
    List<MovimientoResDTO> movimientoListToMovimientoResDTOList(List<Movimiento> movimientoEntityList);
}
