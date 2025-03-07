package ms.core.persona.cliente.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.NoArgsConstructor;
import ms.core.persona.cliente.enums.Genero;

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