package ms.core.persona.cliente.auth.domain;

import ms.core.persona.cliente.auth.infraestructure.dto.AuthLoginReqDTO;
import org.springframework.http.ResponseCookie;

public interface AuthorizationService {
    public ResponseCookie login(AuthLoginReqDTO authLoginReqDTO);
}
