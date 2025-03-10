package jhair.vasquez.ms.core.cuentas.movimientos.cuentas.infraestructure.repository;

import jhair.vasquez.ms.core.cuentas.movimientos.cuentas.domain.Cuenta;
import jhair.vasquez.ms.core.cuentas.movimientos.cuentas.application.repository.CuentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CuentaRepositoryImpl implements CuentaRepository {
    private final CuentaRepositoryJpa cuentaRepositoryJpa;
    @Override
    public Optional<Cuenta> findCuentaByNumCuenta(String numCuenta) {
        return cuentaRepositoryJpa.findCuentaByNumCuenta(numCuenta);
    }

    @Override
    public List<Cuenta> findAll() {
        return cuentaRepositoryJpa.findAll();
    }

    @Override
    public Cuenta save(Cuenta cuenta) {
        return cuentaRepositoryJpa.save(cuenta);
    }

    @Override
    public Optional<List<Cuenta>> findCuentaByClienteId(Long clienteId) {
        return cuentaRepositoryJpa.findCuentaByClienteId(clienteId);
    }
}
