package ms.core.persona.cliente.cliente.application.communication;

import jhair.vasquez.ms.core.dto.kafka.common.ClienteKafkaReqDTO;

public interface KafkaConsumerCommunication {
    void listenClienteRequest(ClienteKafkaReqDTO request);
}
