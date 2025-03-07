package jhair.vasquez.ms.core.cuentas.movimientos.service.strategy.strategy;

import jhair.vasquez.ms.core.cuentas.movimientos.communication.KafkaProducerClient;
import jhair.vasquez.ms.core.cuentas.movimientos.customExceptions.RecordNotFound;
import jhair.vasquez.ms.core.cuentas.movimientos.dtos.res.CuentaConMovimientoDTO;
import jhair.vasquez.ms.core.cuentas.movimientos.dtos.res.ReporteDTO;
import jhair.vasquez.ms.core.cuentas.movimientos.entity.Cuenta;
import jhair.vasquez.ms.core.cuentas.movimientos.entity.Movimiento;
import jhair.vasquez.ms.core.cuentas.movimientos.repository.CuentaRepository;
import jhair.vasquez.ms.core.cuentas.movimientos.repository.MovimientosRepository;
import jhair.vasquez.ms.core.cuentas.movimientos.service.interfaces.ReporteStrategy;
import jhair.vasquez.ms.core.dto.kafka.persona.ClienteKafkaResDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Slf4j
@Component("movementsByAccountAndDateRangeStrategy")
public class MovementsByAccountAndDateRangeStrategy implements ReporteStrategy {
    private final MovimientosRepository movimientosRepository;
    private final CuentaRepository cuentaRepository;
    private final KafkaProducerClient kafkaProducerClient;

    @Override
    public ReporteDTO generateReport(Long clienteId, Date fechaInicio, Date fechaFin) throws RecordNotFound {
        if (fechaInicio.after(fechaFin)) {
            throw new IllegalArgumentException("La fecha de inicio debe ser anterior a la fecha de fin");
        }

        // Validar la existencia del cliente usando Kafka
        log.info("Validando existencia del cliente con ID: {}", clienteId);
        ClienteKafkaResDTO clienteResponse = kafkaProducerClient.fetchCliente(clienteId);
        if (clienteResponse.getClienteId() == null) {
            log.warn("Cliente con ID {} no encontrado en ms-core-persona-cliente", clienteId);
            throw new RecordNotFound("No se encontró el cliente con ID: " + clienteId);
        }
        log.info("Cliente con ID {} validado exitosamente", clienteId);

        // Obtener todas las cuentas del cliente
        List<Cuenta> cuentas = cuentaRepository.findCuentaByClienteId(clienteId)
                .orElseThrow(() -> new RecordNotFound("No se encontraron cuentas para el cliente con ID " + clienteId));

        // Construir el reporte
        List<CuentaConMovimientoDTO> cuentasConMovimientos = cuentas.stream()
                .map(cuenta -> {
                    List<Movimiento> movimientos = movimientosRepository
                            .findMovimientoByCuentaNumAndFechaBetween(cuenta.getNumCuenta(), fechaInicio, fechaFin);

                    BigDecimal saldoActual = movimientosRepository
                            .findMovimientoByCuentaNumOrderByFechaDesc(cuenta.getNumCuenta())
                            .stream()
                            .map(Movimiento::getSaldo)
                            .findFirst()
                            .orElse(cuenta.getSaldoInicial());

                    return CuentaConMovimientoDTO.builder()
                            .cuenta(cuenta)
                            .saldoActual(saldoActual)
                            .movimientos(movimientos)
                            .build();
                })
                .collect(Collectors.toList());

        return ReporteDTO.builder()
                .clienteId(clienteId)
                .cuentas(cuentasConMovimientos)
                .build();
    }
}
