package ms.core.persona.cliente.service.interfaces;

import ms.core.persona.cliente.dtos.AuthLoginReqDTO;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;

public interface AuthorizationService {
    public ResponseCookie login(AuthLoginReqDTO authLoginReqDTO);
}
