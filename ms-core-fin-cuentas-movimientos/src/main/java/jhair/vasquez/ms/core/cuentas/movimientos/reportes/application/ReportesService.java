package jhair.vasquez.ms.core.cuentas.movimientos.reportes.application;

import jhair.vasquez.ms.core.cuentas.movimientos.customExceptions.RecordNotFound;
import jhair.vasquez.ms.core.cuentas.movimientos.reportes.infraestructure.dtos.ReporteDTO;

import java.util.Date;

public interface ReportesService {
    ReporteDTO generateReport(Long clienteId, Date fechaInicio, Date fechaFin, String tipoReporte) throws RecordNotFound;
}
