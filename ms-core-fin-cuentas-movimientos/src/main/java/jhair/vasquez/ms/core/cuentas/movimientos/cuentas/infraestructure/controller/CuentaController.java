package jhair.vasquez.ms.core.cuentas.movimientos.cuentas.infraestructure.controller;

import jhair.vasquez.ms.core.cuentas.movimientos.base.GenericResponse;
import jhair.vasquez.ms.core.cuentas.movimientos.cuentas.infraestructure.dtos.CuentaReqDTO;
import jhair.vasquez.ms.core.cuentas.movimientos.cuentas.infraestructure.dtos.CuentaResDTO;
import jhair.vasquez.ms.core.cuentas.movimientos.cuentas.infraestructure.mapper.CuentaMapper;
import jhair.vasquez.ms.core.cuentas.movimientos.customExceptions.RecordNotFound;
import jhair.vasquez.ms.core.cuentas.movimientos.cuentas.domain.Cuenta;
import jhair.vasquez.ms.core.cuentas.movimientos.cuentas.application.service.CuentaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/cuentas")
public class CuentaController {
    private final CuentaService cuentaService;
    private final CuentaMapper cuentaMapper;

    @GetMapping("/{numCuenta}")
    public GenericResponse<CuentaResDTO> getCuentaByNumCuenta(@PathVariable String numCuenta) throws RecordNotFound {
        return GenericResponse.<CuentaResDTO>builder()
                .status(HttpStatus.OK)
                .message("Transacción exitosa")
                .payload(cuentaMapper.cuentaToCuentaResDTO(cuentaService.findByNumCuenta(numCuenta)))
                .build();

    }
    @GetMapping("/getAll")
    public ResponseEntity<List<Cuenta>> getAllCuentas() {
        List<Cuenta> cuentas = cuentaService.findAll();
        return ResponseEntity.ok(cuentas);
    }

    @PostMapping("/create")
    public GenericResponse<CuentaResDTO> createCuenta(@RequestBody CuentaReqDTO cuentaReq) throws RecordNotFound {
        return GenericResponse.<CuentaResDTO>builder()
                .status(HttpStatus.CREATED)
                .message("Transacción exitosa")
                .payload(cuentaMapper.cuentaToCuentaResDTO(
                        cuentaService.save(cuentaMapper.cuentaReqDTOToCuenta(cuentaReq))))
                .build();
    }
}
