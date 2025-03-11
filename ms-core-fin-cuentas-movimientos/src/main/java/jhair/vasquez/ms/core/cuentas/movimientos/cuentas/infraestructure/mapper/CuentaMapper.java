package jhair.vasquez.ms.core.cuentas.movimientos.cuentas.infraestructure.mapper;


import jhair.vasquez.ms.core.cuentas.movimientos.cuentas.domain.Cuenta;
import jhair.vasquez.ms.core.cuentas.movimientos.cuentas.infraestructure.repository.CuentaEntity;
import jhair.vasquez.ms.core.cuentas.movimientos.cuentas.infraestructure.dtos.CuentaReqDTO;
import jhair.vasquez.ms.core.cuentas.movimientos.cuentas.infraestructure.dtos.CuentaResDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CuentaMapper {
    CuentaMapper INSTANCE = Mappers.getMapper(CuentaMapper.class);

    CuentaReqDTO cuentaToCuentaReqDTO(Cuenta cuentaEntity);
    CuentaResDTO cuentaToCuentaResDTO(Cuenta cuentaEntity);
    Cuenta cuentaReqDTOToCuenta(CuentaReqDTO cuentaReqDTO);

    Cuenta cuentaEntityToCuenta(CuentaEntity cuentaEntity);
    CuentaEntity cuentaToCuentaEntity(Cuenta cuenta);
    List<Cuenta> cuentaEntityListToCuentaList(List<CuentaEntity> cuentaEntity);
}