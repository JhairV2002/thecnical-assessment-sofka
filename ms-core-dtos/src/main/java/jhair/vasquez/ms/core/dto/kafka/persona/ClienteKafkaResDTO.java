package jhair.vasquez.ms.core.dto.kafka.persona;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClienteKafkaResDTO implements Serializable {
    private String correlationId;
    private Long clienteId;
}
