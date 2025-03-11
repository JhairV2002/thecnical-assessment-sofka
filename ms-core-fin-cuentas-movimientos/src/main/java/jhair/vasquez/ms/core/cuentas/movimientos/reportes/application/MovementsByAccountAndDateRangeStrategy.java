package jhair.vasquez.ms.core.cuentas.movimientos.reportes.application;

import jhair.vasquez.ms.core.cuentas.movimientos.cuentas.application.communication.KafkaProducer;
import jhair.vasquez.ms.core.cuentas.movimientos.cuentas.application.repository.CuentaRepository;
import jhair.vasquez.ms.core.cuentas.movimientos.cuentas.domain.Cuenta;
import jhair.vasquez.ms.core.cuentas.movimientos.customExceptions.RecordNotFound;
import jhair.vasquez.ms.core.cuentas.movimientos.movimientos.domain.Movimiento;
import jhair.vasquez.ms.core.cuentas.movimientos.reportes.infraestructure.dtos.CuentaConMovimientoDTO;
import jhair.vasquez.ms.core.cuentas.movimientos.reportes.infraestructure.dtos.ReporteDTO;
import jhair.vasquez.ms.core.cuentas.movimientos.movimientos.application.repository.MovimientosRepository;
import jhair.vasquez.ms.core.dto.kafka.persona.ClienteKafkaResDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
public class MovementsByAccountAndDateRangeStrategy implements ReporteStrategy {
    private final MovimientosRepository movimientosRepository;
    private final CuentaRepository cuentaRepository;
    private final KafkaProducer kafkaProducerClient;

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

        // Obtener todas las cuentaEntities del cliente
        List<Cuenta> cuentaEntities = cuentaRepository.findCuentaByClienteId(clienteId)
                .orElseThrow(() -> new RecordNotFound("No se encontraron cuentaEntities para el cliente con ID " + clienteId));

        // Construir el reporte
        List<CuentaConMovimientoDTO> cuentasConMovimientos = cuentaEntities.stream()
                .map(cuenta -> {
                    List<Movimiento> movimientoEntities = movimientosRepository
                            .findMovimientoByCuentaNumAndFechaBetween(cuenta.getNumCuenta(), fechaInicio, fechaFin);

                    BigDecimal saldoActual = movimientosRepository
                            .findMovimientoByCuentaNumOrderByFechaDesc(cuenta.getNumCuenta())
                            .stream()
                            .map(Movimiento::getSaldo)
                            .findFirst()
                            .orElse(cuenta.getSaldoInicial());

                    return CuentaConMovimientoDTO.builder()
                            .cuentaEntity(cuenta)
                            .saldoActual(saldoActual)
                            .movimientoEntities(movimientoEntities)
                            .build();
                })
                .collect(Collectors.toList());

        return ReporteDTO.builder()
                .clienteId(clienteId)
                .cuentas(cuentasConMovimientos)
                .build();
    }
}
