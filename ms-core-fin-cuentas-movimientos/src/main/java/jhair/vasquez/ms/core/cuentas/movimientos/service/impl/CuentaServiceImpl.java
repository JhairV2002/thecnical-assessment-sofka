package jhair.vasquez.ms.core.cuentas.movimientos.service.impl;

import db.repositorio.financiero.classes.Persona;
import db.repositorio.financiero.dtos.ClienteRequestDTO;
import db.repositorio.financiero.dtos.ClienteResponseDTO;
import db.repositorio.financiero.entity.Cliente;
import db.repositorio.financiero.entity.Cuenta;
import db.repositorio.financiero.repository.ClienteRepository;
import db.repositorio.financiero.repository.CuentaRepository;
import jhair.vasquez.ms.core.cuentas.movimientos.communication.KafkaConsumerClient;
import jhair.vasquez.ms.core.cuentas.movimientos.communication.KafkaProducerClient;
import jhair.vasquez.ms.core.cuentas.movimientos.cons.CuentaCons;
import jhair.vasquez.ms.core.cuentas.movimientos.customExceptions.RecordNotFound;
import jhair.vasquez.ms.core.cuentas.movimientos.service.interfaces.CuentaService;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@AllArgsConstructor
public class CuentaServiceImpl implements CuentaService {
    private final CuentaRepository cuentaRepository;
    private final ClienteRepository clienteRepository;
    // Kafka
    private final KafkaProducerClient kafkaClient;


    @Override
    public Cuenta findByNumCuenta(String numCuenta) throws RecordNotFound {
        Cuenta cuenta = cuentaRepository.findCuentaByNumCuenta(numCuenta);
        if (cuenta == null) {
            throw new RecordNotFound("No se encontró la cuenta con el número: " + numCuenta);
        }
        return cuentaRepository.findCuentaByNumCuenta(numCuenta);
    }

    @Override
    public List<Cuenta> findAll() {
        return cuentaRepository.findAll();
    }

    @Override
    public Cuenta save(Cuenta cuenta) throws RecordNotFound {
        // Valido cliente con kafka
        ClienteResponseDTO clienteResponse = kafkaClient.fetchCliente(cuenta.getClienteId());
        if (clienteResponse.getCliente() == null) {
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
        return cuentaRepository.findCuentaByNumCuenta(numCuenta) != null;
    }

}
