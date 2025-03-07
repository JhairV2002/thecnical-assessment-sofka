package ms.core.persona.cliente.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "clientes", schema = "persona")
public class Cliente extends Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clienteId;
    @Column(nullable = false)
    private String contrasenia;
    @Column(nullable = false)
    private boolean estado = true;
}
