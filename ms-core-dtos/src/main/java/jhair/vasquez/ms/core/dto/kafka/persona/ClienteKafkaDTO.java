package jhair.vasquez.ms.core.dto.kafka.persona;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteKafkaDTO implements Serializable {
    private Long clienteId;
    private String nombre;
    private String apellido;
    private String identificacion;
    private String telefono;
    private String email;
}
