package ms.core.persona.cliente.service.impl;

import ms.core.persona.cliente.entity.Cliente;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

public class UserDetailsImpl implements UserDetails {
    private final ms.core.persona.cliente.entity.Cliente cliente;

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
