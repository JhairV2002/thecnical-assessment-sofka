package jhair.vasquez.ms.core.cuentas.movimientos.reportes.application;


import jhair.vasquez.ms.core.cuentas.movimientos.customExceptions.RecordNotFound;
import jhair.vasquez.ms.core.cuentas.movimientos.reportes.infraestructure.dtos.ReporteDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
@AllArgsConstructor
public class ReportesServiceImpl implements ReportesService{
    private final ReportStrategyFactory reportStrategyFactory;

    @Override
    public ReporteDTO generateReport(Long clienteId, Date fechaInicio, Date fechaFin, String tipoReporte) throws RecordNotFound {
        ReporteStrategy strategy = reportStrategyFactory.getStrategy(tipoReporte);
        log.info("Usando estrategia: {}", strategy.getClass().getSimpleName());
        return strategy.generateReport(clienteId, fechaInicio, fechaFin);
    }
}
