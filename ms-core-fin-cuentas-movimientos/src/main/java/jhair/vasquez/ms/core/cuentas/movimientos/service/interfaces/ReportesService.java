package jhair.vasquez.ms.core.cuentas.movimientos.service.interfaces;

import db.repositorio.financiero.dtos.ReporteDTO;
import db.repositorio.financiero.entity.Movimiento;
import jhair.vasquez.ms.core.cuentas.movimientos.customExceptions.RecordNotFound;

import java.util.Date;
import java.util.List;

public interface ReportesService {
    ReporteDTO generateReport(Long clienteId, Date fechaInicio, Date fechaFin, String tipoReporte) throws RecordNotFound;
}
