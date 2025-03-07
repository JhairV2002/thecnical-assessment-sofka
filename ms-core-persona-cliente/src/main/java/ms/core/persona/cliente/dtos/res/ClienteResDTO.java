package ms.core.persona.cliente.dtos.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ms.core.persona.cliente.enums.Genero;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteResDTO implements Serializable {
    private Long clienteId;
    private String nombres;
    private Genero genero;
    private Integer edad;
    private String identificacion;
    private String direccion;
    private String telefono;
    private boolean estado;
}
