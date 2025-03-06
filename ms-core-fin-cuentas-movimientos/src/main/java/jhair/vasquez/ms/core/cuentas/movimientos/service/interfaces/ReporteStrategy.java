package jhair.vasquez.ms.core.cuentas.movimientos.service.interfaces;

import db.repositorio.financiero.dtos.ReporteDTO;
import jhair.vasquez.ms.core.cuentas.movimientos.customExceptions.RecordNotFound;

import java.util.Date;

public interface ReporteStrategy {
    ReporteDTO generateReport(Long clienteId, Date fechaInicio, Date fechaFin) throws RecordNotFound;
}
