package ms.core.persona.cliente.config.infraestructure.filter;


import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import ms.core.persona.cliente.auth.application.UserDetailsServiceImpl;
import ms.core.persona.cliente.config.application.JWTUtilsImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import java.io.IOException;

@Component
@AllArgsConstructor
public class JWTFilter extends OncePerRequestFilter {
    private final JWTUtilsImpl jwtUtils;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException
    {
        try{
            Cookie tokenCookie =  WebUtils.getCookie(request, "jwt");
            if(WebUtils.getCookie(request, "jwt") != null) {
                String jwtTokenCookie = tokenCookie.getValue();
                DecodedJWT decodedJWT = jwtUtils.validateToken(jwtTokenCookie);
                String username = jwtUtils.extractUsername(decodedJWT);
                // se setea en el security context holder
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    SecurityContext context = SecurityContextHolder.getContext();
                    Authentication authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, null
                    );
                    context.setAuthentication(authentication);
                    SecurityContextHolder.setContext(context);
                }
            }
            filterChain.doFilter(request, response);
        }
        catch (Exception e) {}

    }
}
