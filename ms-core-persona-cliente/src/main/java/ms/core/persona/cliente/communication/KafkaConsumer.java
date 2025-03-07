package ms.core.persona.cliente.communication;

import jhair.vasquez.ms.core.dto.kafka.common.ClienteKafkaReqDTO;
import jhair.vasquez.ms.core.dto.kafka.persona.ClienteKafkaResDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms.core.persona.cliente.cons.KafkaCons;
import ms.core.persona.cliente.customExceptions.RecordNotFoundException;
import ms.core.persona.cliente.entity.Cliente;
import ms.core.persona.cliente.repository.ClienteRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumer {
    private final ClienteRepository clienteRepository;
    private final KafkaTemplate<String, ClienteKafkaResDTO> kafkaTemplate;

    @KafkaListener(topics = KafkaCons.CLIENTE_REQUEST_TOPIC, groupId = KafkaCons.CLIENTE_GROUP_ID)
    public void listenClienteRequest(ClienteKafkaReqDTO request) {
        log.info("Recibida solicitud para clienteId: {} con correlationId: {}", request.getClienteId(), request.getCorrelationId());
        Cliente cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new RecordNotFoundException("No se encontró el cliente con el id: " + request.getClienteId()));

        ClienteKafkaResDTO response = ClienteKafkaResDTO.builder()
                .clienteId(cliente.getClienteId())
                .correlationId(request.getCorrelationId())
                .build();

        kafkaTemplate.send(KafkaCons.CLIENTE_RESPONSE_TOPIC, String.valueOf(request.getClienteId()), response);
        log.info("Enviada respuesta para clienteId: {} con correlationId: {}, response: {}", request.getClienteId(),
                request.getCorrelationId(), response.toString());
    }
}
