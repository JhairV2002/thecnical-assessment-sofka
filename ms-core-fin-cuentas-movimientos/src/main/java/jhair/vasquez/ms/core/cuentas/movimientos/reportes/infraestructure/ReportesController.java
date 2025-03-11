package jhair.vasquez.ms.core.cuentas.movimientos.reportes.infraestructure;

import jhair.vasquez.ms.core.cuentas.movimientos.base.GenericResponse;
import jhair.vasquez.ms.core.cuentas.movimientos.customExceptions.RecordNotFound;
import jhair.vasquez.ms.core.cuentas.movimientos.reportes.infraestructure.dtos.ReporteDTO;
import jhair.vasquez.ms.core.cuentas.movimientos.reportes.application.ReportesService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/reportes")
@AllArgsConstructor
public class ReportesController {

    private final ReportesService reportesService;

    @GetMapping("/reporte")
    public ResponseEntity<GenericResponse<ReporteDTO>> getReporteByCuentaAndFechas(
            @RequestParam("clienteId") Long clienteId,
            @RequestParam("fechaInicio") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaInicio,
            @RequestParam("fechaFin") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaFin,
            @RequestParam("tipoReporte") String tipoReporte) throws RecordNotFound {
        ReporteDTO reporte = reportesService.generateReport(clienteId, fechaInicio, fechaFin, tipoReporte);
        return ResponseEntity.ok(
                GenericResponse.<ReporteDTO>builder()
                        .status(HttpStatusCode.valueOf(HttpStatus.OK.value()))
                        .message("Reporte generado con éxito")
                        .payload(reporte)
                        .build()
        );
    }
}