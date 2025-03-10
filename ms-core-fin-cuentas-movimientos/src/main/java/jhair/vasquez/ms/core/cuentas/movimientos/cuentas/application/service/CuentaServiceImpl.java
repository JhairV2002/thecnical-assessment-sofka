package jhair.vasquez.ms.core.cuentas.movimientos.cuentas.application.service;


import jhair.vasquez.ms.core.cuentas.movimientos.cuentas.domain.CuentaCons;
import jhair.vasquez.ms.core.cuentas.movimientos.cuentas.application.communication.KafkaProducer;
import jhair.vasquez.ms.core.cuentas.movimientos.cuentas.application.repository.CuentaRepository;
import jhair.vasquez.ms.core.cuentas.movimientos.customExceptions.RecordNotFound;
import jhair.vasquez.ms.core.cuentas.movimientos.cuentas.domain.Cuenta;
import jhair.vasquez.ms.core.dto.kafka.persona.ClienteKafkaResDTO;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Random;

@AllArgsConstructor
public class CuentaServiceImpl implements CuentaService {
    private final CuentaRepository cuentaRepository;
    private final KafkaProducer kafkaClient;


    @Override
    public Cuenta findByNumCuenta(String numCuenta) throws RecordNotFound {
        return cuentaRepository.findCuentaByNumCuenta(numCuenta)
                .orElseThrow(() -> new RecordNotFound("No se encontró la cuenta con el número: " + numCuenta));

    }

    @Override
    public List<Cuenta> findAll() {
        return cuentaRepository.findAll();
    }

    @Override
    public Cuenta save(Cuenta cuenta) throws RecordNotFound {
        // Valido cliente con kafka
        ClienteKafkaResDTO clienteResponse = kafkaClient.fetchCliente(cuenta.getClienteId());
        if (clienteResponse.getClienteId() == null) {
            throw new RecordNotFound("No se encontró el cliente con el id: " + cuenta.getClienteId());
        }
        cuenta.setNumCuenta(generateNumCuenta(CuentaCons.CANT_DIG_NUM_CUENTA));
        return cuentaRepository.save(cuenta);
    }

    public String generateNumCuenta(int length) {
        Random random = new Random();
        String numCuenta;
        do {
            StringBuilder numCuentaBuilder = new StringBuilder();
            for (int i = 0; i < length; i++) {
                numCuentaBuilder.append(random.nextInt(10));
            }
            numCuenta = numCuentaBuilder.toString();
        } while (checkNumCuenta(numCuenta));
        return numCuenta;
    }

    public boolean checkNumCuenta(String numCuenta) {
        return cuentaRepository.findCuentaByNumCuenta(numCuenta).isPresent();
    }

}
