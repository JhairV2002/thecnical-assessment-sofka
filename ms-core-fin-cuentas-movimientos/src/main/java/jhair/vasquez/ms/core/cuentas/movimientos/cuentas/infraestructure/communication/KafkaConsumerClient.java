package jhair.vasquez.ms.core.cuentas.movimientos.cuentas.infraestructure.communication;

import com.fasterxml.jackson.databind.ObjectMapper;
import jhair.vasquez.ms.core.cuentas.movimientos.cuentas.domain.Cuenta;
import jhair.vasquez.ms.core.cuentas.movimientos.cuentas.domain.KafkaCons;
import jhair.vasquez.ms.core.cuentas.movimientos.cuentas.application.repository.CuentaRepository;
import jhair.vasquez.ms.core.dto.kafka.common.ClienteKafkaDeleteDTO;
import jhair.vasquez.ms.core.dto.kafka.persona.ClienteKafkaResDTO;
import jhair.vasquez.ms.core.cuentas.movimientos.cuentas.infraestructure.repository.CuentaEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
@AllArgsConstructor
public class KafkaConsumerClient {
    private final CuentaRepository cuentaRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();


    private final Map<String, CompletableFuture<ClienteKafkaResDTO>> responseFutures = new ConcurrentHashMap<>();
    // Consumidor de respuestas
    @KafkaListener(topics = KafkaCons.TOPIC_RESPONSE, groupId = KafkaCons.GROUP_ID)
    public void listenClienteResponse(ConsumerRecord<String, ClienteKafkaResDTO> record) {
        ClienteKafkaResDTO response = record.value();
        String correlationId = response.getCorrelationId();
        log.info("Recibida respuesta de Kafka para correlationId: {}", correlationId);
        KafkaPetitionManager.getInstance().completeResponse(correlationId, response);
    }

    @KafkaListener(topics = KafkaCons.TOPIC_ELIMINACION_LOGICA, groupId = KafkaCons.GROUP_ID)
    @Transactional
    public void onClienteEliminacionLogica(ClienteKafkaDeleteDTO mensaje) {
        Long clienteId = mensaje.getClienteId();
        log.info("Recibida notificación de eliminación lógica para clienteId: {}", clienteId);

        // Buscar y actualizar cuentas
        Optional<List<Cuenta>> cuentasOpt = cuentaRepository.findCuentaByClienteId(clienteId);
        cuentasOpt.ifPresentOrElse(
                cuentas -> {
                    cuentas.forEach(cuenta -> {
                        cuenta.setEstado(Boolean.FALSE);
                        cuentaRepository.save(cuenta);
                        log.debug("CuentaEntity {} actualizada a estado FALSE", cuenta.getNumCuenta());
                    });
                    log.info("Se actualizaron {} cuentas del clienteId {} a estado FALSE", cuentas.size(), clienteId);
                },
                () -> log.info("No se encontraron cuentas asociadas al clienteId {}", clienteId)
        );


        log.info("Cuentas del clienteId {} actualizadas a estado FALSE", clienteId);
    }
}