package jhair.vasquez.ms.core.cuentas.movimientos.cuentas.infraestructure.repository;

import jhair.vasquez.ms.core.cuentas.movimientos.cuentas.application.repository.CuentaRepository;
import jhair.vasquez.ms.core.cuentas.movimientos.cuentas.domain.Cuenta;
import jhair.vasquez.ms.core.cuentas.movimientos.cuentas.infraestructure.mapper.CuentaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class CuentaRepositoryImpl implements CuentaRepository {
    private final CuentaRepositoryJpa cuentaRepositoryJpa;
    private final CuentaMapper cuentaMapper;
    @Override
    public Optional<Cuenta> findCuentaByNumCuenta(String numCuenta) {
        return cuentaRepositoryJpa.findCuentaByNumCuenta(numCuenta).map(cuentaMapper::cuentaEntityToCuenta);
    }

    @Override
    public List<Cuenta> findAll() {
        return cuentaRepositoryJpa.findAll().stream().map(cuentaMapper::cuentaEntityToCuenta).collect(Collectors.toList());
    }

    @Override
    public Cuenta save(Cuenta cuenta) {
        return cuentaMapper.cuentaEntityToCuenta(cuentaRepositoryJpa
                .save(cuentaMapper.cuentaToCuentaEntity(cuenta)));

    }

    @Override
    public Optional<List<Cuenta>> findCuentaByClienteId(Long clienteId) {
        return cuentaRepositoryJpa.findCuentaByClienteId(clienteId)
                .map(cuentaMapper::cuentaEntityListToCuentaList);
    }
}
