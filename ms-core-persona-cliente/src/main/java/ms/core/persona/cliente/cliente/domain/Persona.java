package ms.core.persona.cliente.cliente.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@MappedSuperclass
public class Persona {
    private String nombres;
    @Enumerated(EnumType.STRING)
    private Genero genero;
    private Integer edad;
    @Column(nullable = false, unique = true)
    private String identificacion;
    private String direccion;
    private String telefono;
}