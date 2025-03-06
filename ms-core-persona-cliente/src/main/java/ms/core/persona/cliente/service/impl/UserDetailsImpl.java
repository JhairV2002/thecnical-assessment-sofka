package ms.core.persona.cliente.service.impl;

import db.repositorio.financiero.entity.Cliente;
import db.repositorio.financiero.repository.ClienteRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
