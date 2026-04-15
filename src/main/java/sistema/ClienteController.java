package sistema;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


 //Base URL: http://seuservidor:8080/cliente
@RestController
@RequestMapping("/cliente")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }


    @PostMapping("/registrar")
    public ResponseEntity<Saida> registrar(@Valid @RequestBody Entrada dados) {
        Saida saida = clienteService.buscarOuCriar(dados);
        return ResponseEntity.ok(saida);
    }


    @GetMapping("/{chatId}")
    public ResponseEntity<Saida> buscar(@PathVariable String chatId) {
        Saida saida = clienteService.buscar(chatId);
        return saida.sucesso ? ResponseEntity.ok(saida) : ResponseEntity.notFound().build();
    }


    @PutMapping("/{chatId}")
    public ResponseEntity<Saida> atualizar(@PathVariable String chatId,
                                           @RequestBody Entrada dados) {
        Saida saida = clienteService.atualizar(chatId, dados);
        return saida.sucesso ? ResponseEntity.ok(saida) : ResponseEntity.badRequest().body(saida);
    }
}
