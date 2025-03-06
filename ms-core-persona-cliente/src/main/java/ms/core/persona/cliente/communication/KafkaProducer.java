package ms.core.persona.cliente.communication;

import db.repositorio.financiero.dtos.ClienteLogicalDeleteDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms.core.persona.cliente.cons.KafkaCons;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class KafkaProducer {
    private final KafkaTemplate<String, ClienteLogicalDeleteDTO> kafkaTemplate;

    public void sendEliminacionLogica(Long clienteId) {
        ClienteLogicalDeleteDTO mensaje = new ClienteLogicalDeleteDTO();
        mensaje.setClienteId(clienteId);
        kafkaTemplate.send(KafkaCons.TOPIC_ELIMINACION_LOGICA, String.valueOf(clienteId), mensaje);
        log.info("Mensaje de eliminación lógica enviado a Kafka para clienteId: {}", clienteId);
    }
}
