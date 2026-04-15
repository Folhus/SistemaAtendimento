package sistema;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class Agenda {

    private final SessaoRepository sessaoRepo;
    private final ClienteRepository clienteRepo;

    // O Spring injeta os repositórios automaticamente (injeção por construtor)
    public Agenda(SessaoRepository sessaoRepo, ClienteRepository clienteRepo) {
        this.sessaoRepo = sessaoRepo;
        this.clienteRepo = clienteRepo;
    }


    @Transactional
    public Saida criarSessao(Entrada dados) {
        if (!vago(dados.dataHorario)) {
            return Saida.erro("Data indisponivel: ja existe uma sessao proxima a esse horario");
        }

        Optional<Cliente> clienteOpt = clienteRepo.findByChatId(dados.chatId);
        if (clienteOpt.isEmpty()) {
            return Saida.erro("Cliente nao encontrado");
        }

        Sessao sessao = new Sessao();
        sessao.dataHorario  = dados.dataHorario;
        sessao.procedimento = dados.procedimento;
        sessao.cidade       = dados.cidade;
        sessao.cliente      = clienteOpt.get();
        sessao.confirmado   = false;
        sessao.completo     = false;

        sessaoRepo.save(sessao);

        Saida saida = Saida.ok("Sessao criada com sucesso");
        saida.sessao = Saida.SessaoDTO.de(sessao);
        return saida;
    }


    @Transactional
    public Saida cancelarSessao(Entrada dados) {
        Optional<Sessao> sessaoOpt = sessaoRepo.findByDataHorario(dados.dataHorario);

        if (sessaoOpt.isEmpty()) {
            return Saida.erro("Sessao nao encontrada");
        }

        Sessao sessao = sessaoOpt.get();

        if (!sessao.cliente.chatId.equals(dados.chatId)) {
            return Saida.erro("Esta sessao nao pertence ao cliente informado");
        }

        sessaoRepo.delete(sessao);

        return Saida.ok("Sessao cancelada com sucesso");
    }


    @Transactional(readOnly = true)
    public Saida statusSessao(long dataHorario) {
        Optional<Sessao> sessaoOpt = sessaoRepo.findByDataHorario(dataHorario);

        if (sessaoOpt.isEmpty()) {
            return Saida.erro("Sessao nao encontrada");
        }

        Saida saida = Saida.ok("Ok");
        saida.sessao = Saida.SessaoDTO.de(sessaoOpt.get());
        return saida;
    }


    @Transactional(readOnly = true)
    public List<Saida.SessaoDTO> listarSessoes(String chatId) {
        return sessaoRepo.findByClienteChatId(chatId)
                .stream()
                .map(Saida.SessaoDTO::de)
                .collect(Collectors.toList());
    }


    @Transactional
    public Saida confirmar(Entrada dados) {
        Optional<Sessao> sessaoOpt = sessaoRepo.findByDataHorario(dados.dataHorario);

        if (sessaoOpt.isEmpty()) {
            return Saida.erro("Sessao nao encontrada");
        }

        Sessao sessao = sessaoOpt.get();
        sessao.confirmado = true;
        sessaoRepo.save(sessao);

        Saida saida = Saida.ok("Presenca confirmada com sucesso");
        saida.sessao = Saida.SessaoDTO.de(sessao);
        return saida;
    }


    @Transactional
    public Saida concluir(Long sessaoId) {
        Optional<Sessao> sessaoOpt = sessaoRepo.findById(sessaoId);

        if (sessaoOpt.isEmpty()) {
            return Saida.erro("Sessao nao encontrada");
        }

        Sessao sessao = sessaoOpt.get();
        sessao.completo = true;
        sessaoRepo.save(sessao);

        return Saida.ok("Sessao marcada como concluida");
    }


    private boolean vago(long dataHorario) {
        return !sessaoRepo.existeConflito(dataHorario - 20, dataHorario + 20);
    }
}
