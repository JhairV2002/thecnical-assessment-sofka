package ms.core.persona.cliente.cliente.infraestructure;
import lombok.AllArgsConstructor;
import ms.core.persona.cliente.domain.GenericResponse;
import ms.core.persona.cliente.domain.customExceptions.RecordNotFoundException;
import ms.core.persona.cliente.cliente.infraestructure.dto.ClienteReqDTO;
import ms.core.persona.cliente.cliente.infraestructure.dto.ClienteResDTO;
import ms.core.persona.cliente.cliente.application.ClienteServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
@AllArgsConstructor
public class ClienteController {
    private final ClienteServiceImpl clienteService;

    @GetMapping("/userInfo")
    @ResponseBody
    public GenericResponse<ClienteResDTO> findById() throws RecordNotFoundException {
        return clienteService.getClientInfo();
    }

    @PostMapping("/create")
    public GenericResponse<ClienteResDTO> save(@RequestBody ClienteReqDTO cliente) throws Exception {
        return clienteService.save(cliente);
    }

    @PutMapping("/update")
    public GenericResponse<ClienteResDTO> update(@RequestBody ClienteReqDTO cliente) throws Exception {
        return clienteService.update(cliente);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        clienteService.delete(id);
    }

}
