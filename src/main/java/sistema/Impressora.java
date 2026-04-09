package sistema;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

public class Impressora {

    public void imprimirBanco() {

        java.io.File logsDir = new java.io.File("logs");
        if (!logsDir.exists()) {
            logsDir.mkdirs();
        }

        try (FileWriter writer = new FileWriter("logs/banco" + LocalDateTime.now().toString().replace(":", "-") + ".txt")) {
            writer.write("Clientes:\n");
            for (Map.Entry<String, Cliente> entry : Sistema.gi().banco.clientes.entrySet()) {
                Cliente cliente = entry.getValue();
                if (cliente == null) continue;

                writer.write("  " + (cliente.nome != null ? cliente.nome : "(sem nome)") + " {\n  " + (cliente.contato != null ? cliente.contato : "") + "\n");
                writer.write("  Sessoes:\n");

                if (cliente.sessoes == null || cliente.sessoes.isEmpty()) {
                    writer.write("    (nenhuma sessao)\n");
                } else {
                    for (int i = 0; cliente.sessoes.get(i)!=null; i++) {
                        Sessao sessao = Sistema.gi().banco.sessoes.get(cliente.sessoes.get(i));

                        writer.write("  " + formatarDataHorario(sessao.dataHorario) + " {\n   " +
                                (sessao.procedimento != null ? sessao.procedimento : "") + "\n    " + sessao.completo + "\n    "
                                + sessao.confirmado + "\n    " + (sessao.cidade != null ? sessao.cidade : "") + "\n");
                        writer.write("  }\n");
                    }
                }

                writer.write("}\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String formatarDataHorario(int dataHorario) {
        String dataHorarioStr = String.valueOf(dataHorario);
        String ano = dataHorarioStr.substring(0, 4);
        String mes = dataHorarioStr.substring(4, 6);
        String dia = dataHorarioStr.substring(6, 8);
        String hora = dataHorarioStr.substring(8, 10);
        String minuto = dataHorarioStr.substring(10, 12);
        return dia + "/" + mes + "/" + ano + " " + hora + ":" + minuto;
    }

}
