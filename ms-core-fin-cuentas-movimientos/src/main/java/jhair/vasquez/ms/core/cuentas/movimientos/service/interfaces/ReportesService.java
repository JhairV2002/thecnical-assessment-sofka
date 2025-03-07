package jhair.vasquez.ms.core.cuentas.movimientos.service.interfaces;

import jhair.vasquez.ms.core.cuentas.movimientos.customExceptions.RecordNotFound;
import jhair.vasquez.ms.core.cuentas.movimientos.dtos.res.ReporteDTO;

import java.util.Date;

public interface ReportesService {
    ReporteDTO generateReport(Long clienteId, Date fechaInicio, Date fechaFin, String tipoReporte) throws RecordNotFound;
}
