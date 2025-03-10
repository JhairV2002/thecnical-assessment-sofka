package jhair.vasquez.ms.core.cuentas.movimientos.cuentas.infraestructure.mapper;


import jhair.vasquez.ms.core.cuentas.movimientos.cuentas.infraestructure.dtos.CuentaReqDTO;
import jhair.vasquez.ms.core.cuentas.movimientos.cuentas.domain.Cuenta;
import jhair.vasquez.ms.core.cuentas.movimientos.cuentas.infraestructure.dtos.CuentaResDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CuentaMapper {
    CuentaMapper INSTANCE = Mappers.getMapper(CuentaMapper.class);

    CuentaReqDTO cuentaToCuentaReqDTO(Cuenta cuenta);
    CuentaResDTO cuentaToCuentaResDTO(Cuenta cuenta);
    Cuenta cuentaReqDTOToCuenta(CuentaReqDTO cuentaReqDTO);
}