package ms.core.persona.cliente.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import ms.core.persona.cliente.base.GenericResponse;
import ms.core.persona.cliente.customExceptions.InvalidCredentialsException;
import ms.core.persona.cliente.dtos.AuthLoginReqDTO;
import ms.core.persona.cliente.service.interfaces.AuthorizationService;
import ms.core.persona.cliente.utils.impl.CookiesUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthorizationService authorizationService;
    private final CookiesUtils cookiesUtils;

    @PostMapping("/login")
    public ResponseEntity<GenericResponse<String>> login(@RequestBody AuthLoginReqDTO authLoginReqDTO) {
        try {
            ResponseCookie cookie = authorizationService.login(authLoginReqDTO);
            GenericResponse<String> response = GenericResponse.<String>builder()
                    .status(HttpStatus.OK)
                    .message("Sesión iniciada correctamente")
                    .payload("Sesión iniciada")
                    .build();
            return ResponseEntity.ok()
                    .header("Set-Cookie", cookie.toString())
                    .body(response);
        } catch (InvalidCredentialsException e) {
            GenericResponse<String> errorResponse = GenericResponse.<String>builder()
                    .status(HttpStatus.UNAUTHORIZED)
                    .message(e.getMessage())
                    .payload(null)
                    .build();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        } catch (Exception e) {
            GenericResponse<String> errorResponse = GenericResponse.<String>builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message("Error al procesar el login: " + e.getMessage())
                    .payload(null)
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
