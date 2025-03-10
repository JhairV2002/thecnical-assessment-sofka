package jhair.vasquez.ms.core.cuentas.movimientos.globalHandelException;

import jhair.vasquez.ms.core.cuentas.movimientos.base.GenericResponse;
import jhair.vasquez.ms.core.cuentas.movimientos.movimientos.domain.exception.InsufficientFundsException;
import jhair.vasquez.ms.core.cuentas.movimientos.customExceptions.InvalidStrategyException;
import jhair.vasquez.ms.core.cuentas.movimientos.customExceptions.RecordNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalHandleException {

    @ExceptionHandler(RecordNotFound.class)
    public ResponseEntity<GenericResponse<String>> handleRecordNotFound(RecordNotFound e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(GenericResponse.<String>builder()
                .status(HttpStatus.NOT_FOUND)
                .message(e.getMessage())
                .payload(null)
                .build());
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<GenericResponse<String>> handleInsufficientFundsException(InsufficientFundsException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(GenericResponse.<String>builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(e.getMessage())
                .payload(null)
                .build());
    }

    @ExceptionHandler(InvalidStrategyException.class)
    public ResponseEntity<GenericResponse<Object>> handleInvalidReportStrategy(InvalidStrategyException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(GenericResponse.builder()
                        .status(HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value()))
                        .message(ex.getMessage())
                        .payload(null)
                        .build());
    }

}
