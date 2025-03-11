package ms.core.persona.cliente.cliente.application.communication;

public interface KafkaProducerCommunication {
    void sendEliminacionLogica(Long clienteId);
}
