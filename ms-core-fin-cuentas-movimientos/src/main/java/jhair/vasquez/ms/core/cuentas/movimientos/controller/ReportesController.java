package jhair.vasquez.ms.core.cuentas.movimientos.controller;

import db.repositorio.financiero.dtos.ReporteDTO;
import db.repositorio.financiero.entity.Movimiento;
import jhair.vasquez.ms.core.cuentas.movimientos.base.GenericResponse;
import jhair.vasquez.ms.core.cuentas.movimientos.customExceptions.RecordNotFound;
import jhair.vasquez.ms.core.cuentas.movimientos.service.interfaces.MovimientosService;
import jhair.vasquez.ms.core.cuentas.movimientos.service.interfaces.ReportesService;
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
import java.util.List;

@RestController
@RequestMapping("/reportes")
@AllArgsConstructor
public class ReportesController {

    private final ReportesService reportesService;

    // Otros métodos existentes...

    // Nuevo endpoint para reporte por número de cuenta y rango de fechas
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