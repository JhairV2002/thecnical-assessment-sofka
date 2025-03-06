package ms.core.persona.cliente.globalHandle;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import ms.core.persona.cliente.base.GenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component("accessDeniedHandler")
@RequiredArgsConstructor
public class CustomAcessDeniedHandler implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        GenericResponse<String> genericResponse = GenericResponse.<String>builder()
                .status(HttpStatus.UNAUTHORIZED)
                .message("Algo salió mal :(")
                .payload(null)
                .build();

        String customErrorJson = objectMapper.writeValueAsString(genericResponse);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        response.getWriter().write(customErrorJson);
    }
}
