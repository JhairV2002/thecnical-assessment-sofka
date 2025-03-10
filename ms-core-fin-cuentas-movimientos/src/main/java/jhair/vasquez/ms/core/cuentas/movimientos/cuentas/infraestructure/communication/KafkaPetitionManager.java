package jhair.vasquez.ms.core.cuentas.movimientos.cuentas.infraestructure.communication;


import jhair.vasquez.ms.core.dto.kafka.persona.ClienteKafkaResDTO;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class KafkaPetitionManager {
    private static final KafkaPetitionManager INSTANCE = new KafkaPetitionManager();
    private final Map<String, CompletableFuture<ClienteKafkaResDTO>> responseFutures = new ConcurrentHashMap<>();

    // Constructor privado para evitar instanciación externa
    private KafkaPetitionManager() {}

    // Método para obtener la instancia única
    public static KafkaPetitionManager getInstance() {
        return INSTANCE;
    }

    // Registrar un nuevo future para una solicitud
    public CompletableFuture<ClienteKafkaResDTO> registerResponse(String correlationId) {
        CompletableFuture<ClienteKafkaResDTO> future = new CompletableFuture<>();
        responseFutures.put(correlationId, future);
        log.debug("Registrado future para correlationId: {}", correlationId);
        return future;
    }

    // Completar un future cuando llega una respuesta
    public void completeResponse(String correlationId, ClienteKafkaResDTO response) {
        CompletableFuture<ClienteKafkaResDTO> future = responseFutures.remove(correlationId);
        if (future != null) {
            future.complete(response);
            log.debug("Completado future para correlationId: {}", correlationId);
        } else {
            log.warn("No se encontró future para correlationId: {}", correlationId);
        }
    }


}
