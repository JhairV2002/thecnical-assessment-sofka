package jhair.vasquez.ms.core.cuentas.movimientos.movimientos.infraestructure.config;

import jhair.vasquez.ms.core.cuentas.movimientos.cuentas.application.service.CuentaService;
import jhair.vasquez.ms.core.cuentas.movimientos.movimientos.application.service.MovimientosService;
import jhair.vasquez.ms.core.cuentas.movimientos.movimientos.application.service.MovimientosServiceImpl;
import jhair.vasquez.ms.core.cuentas.movimientos.movimientos.domain.utils.MovimientosUtils;
import jhair.vasquez.ms.core.cuentas.movimientos.movimientos.domain.utils.MovimientosUtilsImpl;
import jhair.vasquez.ms.core.cuentas.movimientos.movimientos.infraestructure.repository.MovimientoRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MovimientosConfig {

    @Bean
    MovimientosService movimientosService(CuentaService cuentaService,
                                          MovimientoRepositoryImpl movimientosRepository,
                                          MovimientosUtils movimientosUtils) {
        return new MovimientosServiceImpl( cuentaService,  movimientosRepository,movimientosUtils);
    }

    @Bean
    MovimientosUtils movimientosUtils() {
        return new MovimientosUtilsImpl();
    }
}
