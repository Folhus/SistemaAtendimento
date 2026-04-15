package sistema;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Base URL: http://seuservidor:8080/agenda

@RestController
@RequestMapping("/agenda")
public class AgendaController {

    private final Agenda agenda;
    private final ClienteService clienteService;

    public AgendaController(Agenda agenda, ClienteService clienteService) {
        this.agenda = agenda;
        this.clienteService = clienteService;
    }

    // ----------------------------------------------------------------
    // POST /agenda/criar
    // Cria uma nova sessão para o cliente
    //
    // Corpo esperado:
    // {
    //   "chatId": "5584999990000@s.whatsapp.net",
    //   "data": "2026/04/15",
    //   "hora": "14:30",
    //   "cidade": "Mossoró",
    //   "procedimento": "Tratamento de Unhas"
    // }
    // ----------------------------------------------------------------
    @PostMapping("/criar")
    public ResponseEntity<Saida> criar(@Valid @RequestBody Entrada dados) {
        // Garante que o cliente existe antes de agendar
        clienteService.buscarOuCriar(dados);
        dados.calcularDataHorario();
        Saida saida = agenda.criarSessao(dados);
        return saida.sucesso ? ResponseEntity.ok(saida) : ResponseEntity.badRequest().body(saida);
    }

    // ----------------------------------------------------------------
    // POST /agenda/cancelar
    // Cancela uma sessão existente
    //
    // Corpo esperado:
    // {
    //   "chatId": "5584999990000@s.whatsapp.net",
    //   "data": "2026/04/15",
    //   "hora": "14:30"
    // }
    // ----------------------------------------------------------------
    @PostMapping("/cancelar")
    public ResponseEntity<Saida> cancelar(@Valid @RequestBody Entrada dados) {
        dados.calcularDataHorario();
        Saida saida = agenda.cancelarSessao(dados);
        return saida.sucesso ? ResponseEntity.ok(saida) : ResponseEntity.badRequest().body(saida);
    }

    // ----------------------------------------------------------------
    // GET /agenda/status?dataHorario=202604151430
    // Consulta o status de uma sessão
    // ----------------------------------------------------------------
    @GetMapping("/status")
    public ResponseEntity<Saida> status(@RequestParam long dataHorario) {
        Saida saida = agenda.statusSessao(dataHorario);
        return saida.sucesso ? ResponseEntity.ok(saida) : ResponseEntity.notFound().build();
    }

    // ----------------------------------------------------------------
    // GET /agenda/listar?chatId=5584999990000@s.whatsapp.net
    // Lista todas as sessões de um cliente
    // ----------------------------------------------------------------
    @GetMapping("/listar")
    public ResponseEntity<List<Saida.SessaoDTO>> listar(@RequestParam String chatId) {
        return ResponseEntity.ok(agenda.listarSessoes(chatId));
    }

    // ----------------------------------------------------------------
    // POST /agenda/confirmar
    // Cliente confirma presença na sessão
    // ----------------------------------------------------------------
    @PostMapping("/confirmar")
    public ResponseEntity<Saida> confirmar(@Valid @RequestBody Entrada dados) {
        dados.calcularDataHorario();
        Saida saida = agenda.confirmar(dados);
        return saida.sucesso ? ResponseEntity.ok(saida) : ResponseEntity.badRequest().body(saida);
    }

    // ----------------------------------------------------------------
    // POST /agenda/concluir/{id}
    // Marca uma sessão como concluída (uso do painel web / podóloga)
    // ----------------------------------------------------------------
    @PostMapping("/concluir/{id}")
    public ResponseEntity<Saida> concluir(@PathVariable Long id) {
        Saida saida = agenda.concluir(id);
        return saida.sucesso ? ResponseEntity.ok(saida) : ResponseEntity.badRequest().body(saida);
    }
}
