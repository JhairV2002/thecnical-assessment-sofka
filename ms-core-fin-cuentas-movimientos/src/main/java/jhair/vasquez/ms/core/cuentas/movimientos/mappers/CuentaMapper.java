package jhair.vasquez.ms.core.cuentas.movimientos.mappers;


import jhair.vasquez.ms.core.cuentas.movimientos.dtos.req.CuentaReqDTO;
import jhair.vasquez.ms.core.cuentas.movimientos.entity.Cuenta;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CuentaMapper {
    CuentaMapper INSTANCE = Mappers.getMapper(CuentaMapper.class);

    CuentaReqDTO cuentaToCuentaReqDTO(Cuenta cuenta);
}