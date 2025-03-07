package jhair.vasquez.ms.core.dto.kafka.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClienteKafkaDeleteDTO implements Serializable {
    private Long clienteId;
}
