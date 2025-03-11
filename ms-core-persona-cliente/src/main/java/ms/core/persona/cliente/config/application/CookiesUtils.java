package ms.core.persona.cliente.config.application;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CookiesUtils {
    private final JWTUtilsImpl jwtUtils;

    public ResponseCookie createCookie(String name, String jwt, Long expiration) {
        return ResponseCookie.from(name, jwt)
                .maxAge(expiration)
                .httpOnly(true)
                .path("/")
                .secure(false)
                .domain("localhost")
                .build();
    }

    public void deleteCookie(String name, HttpServletResponse response) {
        ResponseCookie responseCookie = ResponseCookie.from(name, "")
                .maxAge(0)
                .httpOnly(true)
                .path("/")
                .build();
        response.setHeader("Set-Cookie", responseCookie.toString());
    }
}
