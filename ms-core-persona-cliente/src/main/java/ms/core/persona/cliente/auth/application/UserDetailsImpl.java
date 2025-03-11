package ms.core.persona.cliente.auth.application;

import ms.core.persona.cliente.cliente.domain.Cliente;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

public class UserDetailsImpl implements UserDetails {
    private final Cliente cliente;

    UserDetailsImpl(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return cliente.getContrasenia();
    }

    @Override
    public String getUsername() {
        return cliente.getIdentificacion();
    }

    @Override
    public boolean isEnabled() {
        return cliente.isEstado();
    }
}
