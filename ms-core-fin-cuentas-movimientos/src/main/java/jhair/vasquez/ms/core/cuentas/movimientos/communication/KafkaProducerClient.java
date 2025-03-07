package jhair.vasquez.ms.core.cuentas.movimientos.communication;

import jhair.vasquez.ms.core.cuentas.movimientos.cons.KafkaCons;
import jhair.vasquez.ms.core.cuentas.movimientos.customExceptions.RecordNotFound;

import jhair.vasquez.ms.core.dto.kafka.common.ClienteKafkaReqDTO;
import jhair.vasquez.ms.core.dto.kafka.persona.ClienteKafkaResDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Component
@AllArgsConstructor
@Slf4j
public class KafkaProducerClient {
    private final KafkaTemplate<String, ClienteKafkaReqDTO> kafkaTemplate;

    // Método para enviar solicitud y esperar respuesta
    public ClienteKafkaResDTO fetchCliente(Long clienteId) throws RecordNotFound {
        String correlationId = UUID.randomUUID().toString();
        ClienteKafkaReqDTO request = ClienteKafkaReqDTO.builder()
                .clienteId(clienteId)
                .correlationId(correlationId)
                .build();

        CompletableFuture<ClienteKafkaResDTO> future = KafkaPetitionManager.getInstance().registerResponse(correlationId);
        kafkaTemplate.send(KafkaCons.REQUEST_TOPIC, String.valueOf(clienteId), request);
        log.info("Solicitud enviada a Kafka para clienteId: {} con correlationId: {}", clienteId, correlationId);

        try {
            ClienteKafkaResDTO response = future.get(10, TimeUnit.SECONDS);
            log.info("Respuesta recibida para clienteId: {} con correlationId: {}, response: {}",
                    clienteId, correlationId, response);
            return response;
        } catch (Exception e) {
            log.error("Error al obtener respuesta de Kafka para clienteId: {}", clienteId, e);
            throw new RecordNotFound("No se pudo obtener información del cliente con ID: " + clienteId);
        }
    }
}
