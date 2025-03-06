package ms.core.persona.cliente.communication;

import db.repositorio.financiero.dtos.ClienteRequestDTO;
import db.repositorio.financiero.dtos.ClienteResponseDTO;
import db.repositorio.financiero.entity.Cliente;
import db.repositorio.financiero.repository.ClienteRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms.core.persona.cliente.cons.KafkaCons;
import ms.core.persona.cliente.customExceptions.RecordNotFoundException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class KafkaConsumer {
    private final ClienteRepository clienteRepository;
    private final KafkaTemplate<String, ClienteResponseDTO> kafkaTemplate;

    @KafkaListener(topics = KafkaCons.CLIENTE_REQUEST_TOPIC, groupId = KafkaCons.CLIENTE_GROUP_ID)
    public void listenClienteRequest(ClienteRequestDTO request) {
        log.info("Recibida solicitud para clienteId: {} con correlationId: {}", request.getClienteId(), request.getCorrelationId());
        Cliente cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new RecordNotFoundException("No se encontró el cliente con el id: " + request.getClienteId()));

        ClienteResponseDTO response = new ClienteResponseDTO();
        response.setCliente(cliente);
        response.setCorrelationId(request.getCorrelationId());

        kafkaTemplate.send(KafkaCons.CLIENTE_RESPONSE_TOPIC, String.valueOf(request.getClienteId()), response);
        log.info("Enviada respuesta para clienteId: {} con correlationId: {}, response: {}", request.getClienteId(),
                request.getCorrelationId(), response.toString());
    }
}
