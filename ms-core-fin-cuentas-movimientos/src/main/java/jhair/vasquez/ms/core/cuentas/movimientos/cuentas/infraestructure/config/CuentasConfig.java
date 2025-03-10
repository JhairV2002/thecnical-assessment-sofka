package jhair.vasquez.ms.core.cuentas.movimientos.cuentas.infraestructure.config;

import jhair.vasquez.ms.core.cuentas.movimientos.cuentas.application.communication.KafkaProducer;
import jhair.vasquez.ms.core.cuentas.movimientos.cuentas.application.repository.CuentaRepository;
import jhair.vasquez.ms.core.cuentas.movimientos.cuentas.application.service.CuentaService;
import jhair.vasquez.ms.core.cuentas.movimientos.cuentas.application.service.CuentaServiceImpl;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CuentasConfig {

    @Bean
    public CuentaService cuentaService(CuentaRepository cuentaRepository, KafkaProducer kafkaProducer) {
        return new CuentaServiceImpl(cuentaRepository, kafkaProducer);
    }
}
