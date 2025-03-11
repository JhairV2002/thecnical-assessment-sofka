package ms.core.persona.cliente.application.exceptions;

public class InvalidFieldException extends RuntimeException{
    public InvalidFieldException(String message) {
        super(message);
    }
}
