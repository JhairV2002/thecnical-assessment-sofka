package ms.core.persona.cliente.config.infraestructure;

import lombok.AllArgsConstructor;
import ms.core.persona.cliente.config.infraestructure.filter.JWTFilter;
import ms.core.persona.cliente.auth.infraestructure.CustomAcessDeniedHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuración de seguridad para la aplicación.
 * Define las reglas de autorización, políticas de sesión y filtros personalizados para la autenticación
 * basada en JWT.
 */
@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {
    private final JWTFilter jwtFilter;
    private final UserDetailsService userDetailsService;
    private final AuthenticationProvider authenticationProvider;
    private final CustomAcessDeniedHandler customAcessDeniedHandler;

    /**
     * Configura la cadena de filtros de seguridad para la aplicación.
     * Establece las reglas de autorización, desactiva CSRF, configura la política de sesiones como sin estado,
     * y añade un filtro personalizado para la autenticación JWT.
     *
     * @param http Objeto HttpSecurity para configurar la seguridad web.
     * @return Una instancia de {@link SecurityFilterChain} configurada.
     * @throws Exception si ocurre un error durante la configuración de la cadena de filtros.
     */

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/auth/**").permitAll() // Permitir acceso público al endpoint de autenticación
                        .anyRequest().authenticated() // Proteger todas las demás rutas
                ).sessionManagement(
                        sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(
                        exceptionHandling -> exceptionHandling
                                .authenticationEntryPoint(customAcessDeniedHandler));


        return http.build();
    }
}
