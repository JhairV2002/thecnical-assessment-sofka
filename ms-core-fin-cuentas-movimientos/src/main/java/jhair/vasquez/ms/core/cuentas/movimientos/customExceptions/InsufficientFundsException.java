package jhair.vasquez.ms.core.cuentas.movimientos.customExceptions;

public class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String message) {
        super(message);
    }
}
