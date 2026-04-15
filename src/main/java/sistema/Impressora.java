package sistema;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Service
public class Impressora {

    private final ClienteRepository clienteRepo;
    private final SessaoRepository sessaoRepo;

    public Impressora(ClienteRepository clienteRepo, SessaoRepository sessaoRepo) {
        this.clienteRepo = clienteRepo;
        this.sessaoRepo = sessaoRepo;
    }

    @Transactional(readOnly = true)
    public void imprimirBanco() {
        try {
            Path logsDir = Path.of("logs");
            Files.createDirectories(logsDir);

            String nomeArquivo = "logs/banco_"
                    + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"))
                    + ".txt";

            try (FileWriter writer = new FileWriter(nomeArquivo)) {
                writer.write("=== RELATÓRIO DO BANCO ===\n");
                writer.write("Gerado em: " + LocalDateTime.now() + "\n\n");

                List<Cliente> clientes = clienteRepo.findAll();
                writer.write("Total de clientes: " + clientes.size() + "\n\n");

                for (Cliente cliente : clientes) {
                    writer.write("Cliente: " + cliente.nome + "\n");
                    writer.write("  Contato : " + cliente.contato + "\n");
                    writer.write("  ChatId  : " + cliente.chatId + "\n");
                    writer.write("  Sessoes :\n");

                    List<Sessao> sessoes = sessaoRepo.findByClienteChatId(cliente.chatId);

                    if (sessoes.isEmpty()) {
                        writer.write("    (nenhuma sessao)\n");
                    } else {
                        for (Sessao sessao : sessoes) {
                            writer.write("    - " + formatarDataHorario(sessao.dataHorario) + "\n");
                            writer.write("      Procedimento : " + sessao.procedimento + "\n");
                            writer.write("      Cidade       : " + sessao.cidade + "\n");
                            writer.write("      Confirmado   : " + sessao.confirmado + "\n");
                            writer.write("      Completo     : " + sessao.completo + "\n");
                        }
                    }
                    writer.write("\n");
                }
            }

            System.out.println("Log gerado: " + nomeArquivo);

        } catch (IOException e) {
            System.err.println("Erro ao gerar log: " + e.getMessage());
        }
    }

    private String formatarDataHorario(long dataHorario) {
        String s = String.valueOf(dataHorario);
        if (s.length() < 12) return s;
        return s.substring(6, 8) + "/" + s.substring(4, 6) + "/" + s.substring(0, 4)
                + " " + s.substring(8, 10) + ":" + s.substring(10, 12);
    }
}
