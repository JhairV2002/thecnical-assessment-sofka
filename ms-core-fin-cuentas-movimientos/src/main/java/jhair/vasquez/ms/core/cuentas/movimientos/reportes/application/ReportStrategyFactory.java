package jhair.vasquez.ms.core.cuentas.movimientos.reportes.application;

import jhair.vasquez.ms.core.cuentas.movimientos.customExceptions.InvalidStrategyException;

import java.util.Map;

public class ReportStrategyFactory {
    private final Map<String, ReporteStrategy> strategies;

    public ReportStrategyFactory(Map<String, ReporteStrategy> strategies) {
        this.strategies = strategies;
    }

    public ReporteStrategy getStrategy(String reportType) {
        ReporteStrategy strategy = strategies.get(reportType);
        if (strategy == null) {
            throw new InvalidStrategyException("El tipo de reporte '" + reportType + "' no es válido o no está registrado");
        }
        return strategy;
    }
}
