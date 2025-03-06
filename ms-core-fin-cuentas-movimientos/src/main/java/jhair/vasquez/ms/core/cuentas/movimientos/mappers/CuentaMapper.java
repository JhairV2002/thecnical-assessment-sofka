package jhair.vasquez.ms.core.cuentas.movimientos.mappers;

import db.repositorio.financiero.dtos.CuentaDTO;
import db.repositorio.financiero.entity.Cuenta;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CuentaMapper {
    CuentaMapper INSTANCE = Mappers.getMapper(CuentaMapper.class);

    CuentaDTO cuentaToCuentaDTO(Cuenta cuenta);
    List<CuentaDTO> cuentaListoToCuentaDTO(List<Cuenta> cuentaList);
    Cuenta cuentaDTOToCuenta(CuentaDTO cuentaDTO);
}