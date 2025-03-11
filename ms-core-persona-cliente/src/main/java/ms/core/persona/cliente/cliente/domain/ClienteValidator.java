package ms.core.persona.cliente.cliente.domain;

import org.springframework.validation.Errors;

public interface ClienteValidator {
    private void validateIdentificacion(String identificacion, Errors errors) {}
    private void validateTelefono(String telefono, Errors errors){};
}
