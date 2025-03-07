package ms.core.persona.cliente.controller;
import lombok.AllArgsConstructor;
import ms.core.persona.cliente.base.GenericResponse;
import ms.core.persona.cliente.customExceptions.RecordNotFoundException;
import ms.core.persona.cliente.dtos.req.ClienteReqDTO;
import ms.core.persona.cliente.dtos.res.ClienteResDTO;
import ms.core.persona.cliente.service.impl.ClienteServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
