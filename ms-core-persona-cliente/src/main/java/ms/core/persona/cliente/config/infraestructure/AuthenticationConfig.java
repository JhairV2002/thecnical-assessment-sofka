package ms.core.persona.cliente.config.infraestructure;

import lombok.RequiredArgsConstructor;
import ms.core.persona.cliente.domain.customExceptions.RecordNotFoundException;
import ms.core.persona.cliente.cliente.domain.Cliente;

import ms.core.persona.cliente.cliente.infraestructure.repository.ClienteRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

/**
 * Configuración de autenticación para la aplicación.
 * Define beans relacionados con la autenticación, incluyendo el servicio de detalles de usuario,
 * el proveedor de autenticación, el administrador de autenticación y el codificador de contraseñas.
 */
@Configuration
@RequiredArgsConstructor
public class AuthenticationConfig {
    private final ClienteRepositoryImpl clienteRepository;

    /**
     * Crea un {@link UserDetailsService} que carga los detalles del usuario desde el repositorio de clientes
     * basado en la identificación del cliente.
     *
     * @return Una implementación de {@link UserDetailsService} que retorna un {@link User} con los detalles del cliente.
     * @throws RecordNotFoundException si no se encuentra un cliente con la identificación proporcionada.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            final Cliente cliente = clienteRepository.findClienteByIdentificacion(username)
                    .orElseThrow(() -> new RecordNotFoundException("Cliente no encontrado"));
            return User.builder()
                    .username(cliente.getIdentificacion())
                    .password(cliente.getContrasenia())
                    .authorities(List.of())
                    .build();
        };
    }

    /**
     * Crea un {@link AuthenticationProvider} que utiliza el {@link UserDetailsService} y el codificador de contraseñas
     * para autenticar usuarios.
     *
     * @return Una instancia de {@link DaoAuthenticationProvider} configurada para autenticación basada en DAO.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    /**
     * Crea un {@link AuthenticationManager} para gestionar la autenticación de usuarios.
     *
     * @param authConfig Configuración de autenticación proporcionada por Spring Security.
     * @return Una instancia de {@link AuthenticationManager}.
     * @throws Exception si ocurre un error al obtener el administrador de autenticación.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    /**
     * Crea un {@link PasswordEncoder} para codificar y comparar contraseñas.
     * Actualmente utiliza {@link NoOpPasswordEncoder}, que no realiza codificación (para pruebas).
     *
     * @return Una instancia de {@link PasswordEncoder}.
     * @deprecated El uso de {@link NoOpPasswordEncoder} no es seguro para producción; considera usar BCryptPasswordEncoder.
     */
    @Bean
    @Deprecated
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
