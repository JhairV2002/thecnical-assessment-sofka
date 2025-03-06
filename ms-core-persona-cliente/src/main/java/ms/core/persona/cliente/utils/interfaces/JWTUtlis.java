package ms.core.persona.cliente.utils.interfaces;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.Authentication;

import java.util.Map;

public interface JWTUtlis {
    public String generateJWT(Authentication authentication);
    public DecodedJWT decodeJWT(String jwt);
    public String getUserNameFromJWT(DecodedJWT decodedJWT);
    public Map<String, Object> getClaims(DecodedJWT jwt);
}
