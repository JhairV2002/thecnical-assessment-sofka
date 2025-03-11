package ms.core.persona.cliente.domain.customExceptions;

public class RecordAlreadyExistsException extends RuntimeException{
    public RecordAlreadyExistsException(String message) {
        super(message);
    }
}
