package jhair.vasquez.ms.core.cuentas.movimientos.service.impl;


import jhair.vasquez.ms.core.cuentas.movimientos.customExceptions.InvalidStrategyException;
import jhair.vasquez.ms.core.cuentas.movimientos.customExceptions.RecordNotFound;
import jhair.vasquez.ms.core.cuentas.movimientos.dtos.res.ReporteDTO;
import jhair.vasquez.ms.core.cuentas.movimientos.service.interfaces.ReporteStrategy;
import jhair.vasquez.ms.core.cuentas.movimientos.service.interfaces.ReportesService;
import jhair.vasquez.ms.core.cuentas.movimientos.service.strategy.strategy.MovementsByAccountAndDateRangeStrategy;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
@AllArgsConstructor
public class ReportesServiceImpl implements ReportesService{
    private final BeanFactory beanFactory;

    @Override
    public ReporteDTO generateReport(Long clienteId, Date fechaInicio, Date fechaFin, String tipoReporte) throws RecordNotFound {
        if (!beanFactory.containsBean(tipoReporte)) {
            log.error("Tipo de reporte no válido: {}", tipoReporte);
            throw new InvalidStrategyException("El tipo de reporte '" + tipoReporte + "' no es válido o no está registrado");
        }

        try {
            // Obtener la estrategia por el nombre del bean
            ReporteStrategy strategy = beanFactory.getBean(tipoReporte, ReporteStrategy.class);
            log.info("Usando estrategia: {}", strategy.getClass().getSimpleName());
            return strategy.generateReport(clienteId, fechaInicio, fechaFin);
        } catch (NoSuchBeanDefinitionException e) {
            // Este catch es redundante con containsBean, pero lo dejamos por seguridad
            log.error("No se encontró la estrategia para el tipo de reporte: {}", tipoReporte, e);
            throw new InvalidStrategyException("El tipo de reporte '" + tipoReporte + "' no está registrado");
        }
    }
}
