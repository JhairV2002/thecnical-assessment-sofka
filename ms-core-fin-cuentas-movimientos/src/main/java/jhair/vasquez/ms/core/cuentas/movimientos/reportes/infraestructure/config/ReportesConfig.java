package jhair.vasquez.ms.core.cuentas.movimientos.reportes.infraestructure.config;

import jhair.vasquez.ms.core.cuentas.movimientos.cuentas.application.repository.CuentaRepository;
import jhair.vasquez.ms.core.cuentas.movimientos.cuentas.infraestructure.communication.KafkaProducerClient;
import jhair.vasquez.ms.core.cuentas.movimientos.movimientos.application.repository.MovimientosRepository;
import jhair.vasquez.ms.core.cuentas.movimientos.reportes.application.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ReportesConfig {
    @Bean
    public ReportStrategyFactory reportStrategyFactory(
            MovimientosRepository  movimientosRepository,
            CuentaRepository cuentaRepository,
            KafkaProducerClient kafkaProducerClient
    ) {
        Map<String, ReporteStrategy> strategies = new HashMap<>();
        strategies.put("movementsByAccountAndDateRange",
                new MovementsByAccountAndDateRangeStrategy(movimientosRepository, cuentaRepository, kafkaProducerClient));
        return new ReportStrategyFactory(strategies);
    }

    @Bean
    public ReportesService reportesService(ReportStrategyFactory reportStrategyFactory) {
        return new ReportesServiceImpl(reportStrategyFactory);
    }
}
