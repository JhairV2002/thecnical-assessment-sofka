package ms.core.persona.cliente.cliente.infraestructure.communication;

import jhair.vasquez.ms.core.dto.kafka.common.ClienteKafkaDeleteDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms.core.persona.cliente.cliente.application.communication.KafkaProducerCommunication;
import ms.core.persona.cliente.domain.cons.KafkaCons;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class KafkaProducer implements KafkaProducerCommunication {
    private final KafkaTemplate<String, ClienteKafkaDeleteDTO> kafkaTemplate;

    @Override
    public void sendEliminacionLogica(Long clienteId) {
        ClienteKafkaDeleteDTO mensaje = ClienteKafkaDeleteDTO.builder().clienteId(clienteId).build();
        kafkaTemplate.send(KafkaCons.TOPIC_ELIMINACION_LOGICA, String.valueOf(clienteId), mensaje);
        log.info("Mensaje de eliminación lógica enviado a Kafka para clienteId: {}", clienteId);
    }
}
