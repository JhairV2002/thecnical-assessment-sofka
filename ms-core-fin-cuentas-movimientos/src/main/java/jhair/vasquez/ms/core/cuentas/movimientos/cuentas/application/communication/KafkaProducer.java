package jhair.vasquez.ms.core.cuentas.movimientos.cuentas.application.communication;

import jhair.vasquez.ms.core.cuentas.movimientos.customExceptions.RecordNotFound;
import jhair.vasquez.ms.core.dto.kafka.persona.ClienteKafkaResDTO;

public interface KafkaProducer {
    ClienteKafkaResDTO fetchCliente(Long clienteId) throws RecordNotFound;
}
