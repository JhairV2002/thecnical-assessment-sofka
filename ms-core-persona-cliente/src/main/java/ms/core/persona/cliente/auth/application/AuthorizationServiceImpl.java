package ms.core.persona.cliente.auth.application;

import lombok.RequiredArgsConstructor;
import ms.core.persona.cliente.domain.customExceptions.InvalidCredentialsException;
import ms.core.persona.cliente.auth.infraestructure.dto.AuthLoginReqDTO;
import ms.core.persona.cliente.auth.domain.AuthorizationService;
import ms.core.persona.cliente.config.application.CookiesUtils;
import ms.core.persona.cliente.config.application.JWTUtilsImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorizationServiceImpl implements AuthorizationService {
    private final UserDetailsService userDetailsService;
    private final JWTUtilsImpl jwtUtils;
    private final CookiesUtils cookiesUtils;
    private final AuthenticationManager authenticationManager;
    @Value("${security.jwt.expiration}")
    private Long JWT_EXPIRATION;

    @Override
    public ResponseCookie login(AuthLoginReqDTO authLoginReqDTO) {
        try{
            String username = authLoginReqDTO.username();
            String password = authLoginReqDTO.password();
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token =  jwtUtils.generateToken(authentication);
            return cookiesUtils.createCookie("jwt", token, JWT_EXPIRATION);
        } catch (AuthenticationException e) {
            throw new InvalidCredentialsException("Usuario o contraseña incorrectos");
        } catch (Exception e) {
            throw new RuntimeException("Error al procesar el login", e);
        }
    }
}
