package jhair.vasquez.ms.core.cuentas.movimientos.reportes.application;

import jhair.vasquez.ms.core.cuentas.movimientos.customExceptions.RecordNotFound;
import jhair.vasquez.ms.core.cuentas.movimientos.reportes.infraestructure.dtos.ReporteDTO;

import java.util.Date;

public interface ReporteStrategy {
    ReporteDTO generateReport(Long clienteId, Date fechaInicio, Date fechaFin) throws RecordNotFound;
}
