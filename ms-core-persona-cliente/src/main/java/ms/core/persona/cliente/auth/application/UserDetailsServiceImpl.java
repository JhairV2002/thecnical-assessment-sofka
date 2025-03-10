package ms.core.persona.cliente.auth.application;

import lombok.AllArgsConstructor;
import ms.core.persona.cliente.cliente.domain.ClienteRepository;
import ms.core.persona.cliente.cliente.domain.Cliente;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final ClienteRepository clienteRepository;
    @Override
    public UserDetails loadUserByUsername(String identificacion) throws UsernameNotFoundException {
        Cliente cliente = clienteRepository.findClienteByIdentificacion(identificacion)
                .orElseThrow(() -> new UsernameNotFoundException("No se encontró el cliente con la identificación: " + identificacion));
        return new UserDetailsImpl(cliente);
    }
}
