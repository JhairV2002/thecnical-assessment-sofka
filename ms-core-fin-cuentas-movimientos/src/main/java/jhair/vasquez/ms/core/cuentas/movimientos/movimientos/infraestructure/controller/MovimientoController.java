package jhair.vasquez.ms.core.cuentas.movimientos.movimientos.infraestructure.controller;

import jhair.vasquez.ms.core.cuentas.movimientos.base.GenericResponse;
import jhair.vasquez.ms.core.cuentas.movimientos.movimientos.domain.Movimiento;
import jhair.vasquez.ms.core.cuentas.movimientos.movimientos.infraestructure.repository.MovimientoEntity;
import jhair.vasquez.ms.core.cuentas.movimientos.movimientos.domain.exception.InsufficientFundsException;
import jhair.vasquez.ms.core.cuentas.movimientos.customExceptions.RecordNotFound;
import jhair.vasquez.ms.core.cuentas.movimientos.movimientos.application.service.MovimientosService;
import jhair.vasquez.ms.core.cuentas.movimientos.movimientos.infraestructure.dtos.req.MovimientoReqDTO;
import jhair.vasquez.ms.core.cuentas.movimientos.movimientos.infraestructure.dtos.res.MovimientoResDTO;
import jhair.vasquez.ms.core.cuentas.movimientos.movimientos.infraestructure.mapper.MovimientosMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/movimientos")
@AllArgsConstructor
public class MovimientoController {

    private final MovimientosService movimientosService;
    private final MovimientosMapper movimientosMapper;

    // Obtener todos los movimientoEntities
    @GetMapping
    public ResponseEntity<GenericResponse<List<MovimientoResDTO>>> findAll() {
        List<MovimientoResDTO> movimientos = movimientosService.findAll().stream()
                .map(movimientosMapper::movimientoToMovimientoResDTO).collect(Collectors.toList());
        return ResponseEntity.ok(
                GenericResponse.<List<MovimientoResDTO>>builder()
                        .status(HttpStatusCode.valueOf(HttpStatus.OK.value()))
                        .message("Movimientos obtenidos con éxito")
                        .payload(movimientos)
                        .build()
        );
    }

    // Obtener movimientoEntities por número de cuentaEntity
    @GetMapping("/cuenta/{numCuenta}")
    public ResponseEntity<GenericResponse<List<MovimientoResDTO>>> findByCuentaNum(@PathVariable String numCuenta) {
        List<MovimientoResDTO> movimientos = movimientosService.findMovimientoByCuentaNum(numCuenta)
                .stream().map(movimientosMapper::movimientoToMovimientoResDTO).collect(Collectors.toList());
        return ResponseEntity.ok(
                GenericResponse.<List<MovimientoResDTO>>builder()
                        .status(HttpStatusCode.valueOf(HttpStatus.OK.value()))
                        .message("Movimientos de la cuentaEntity " + numCuenta + " obtenidos con éxito")
                        .payload(movimientos)
                        .build()
        );
    }

    // Crear un nuevo movimiento
    @PostMapping
    public ResponseEntity<GenericResponse<MovimientoResDTO>> saveMovimiento(
            @RequestBody MovimientoReqDTO movimiento)
            throws RecordNotFound, InsufficientFundsException {
        Movimiento nuevoMovimientoEntity = movimientosService.saveMovimiento(
                movimientosMapper.movimientoReqDTOToMovimientoEntity(movimiento)
        );
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(GenericResponse.<MovimientoResDTO>builder()
                        .status(HttpStatusCode.valueOf(HttpStatus.CREATED.value()))
                        .message("MovimientoEntity creado con éxito")
                        .payload(movimientosMapper.movimientoToMovimientoResDTO(nuevoMovimientoEntity))
                        .build()
                );
    }
}
