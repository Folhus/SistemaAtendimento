package sistema;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO (Data Transfer Object) que representa os dados enviados pelo bot ou site.
 *
 * Exemplo de JSON enviado pelo bot Node.js:
 * {
 *   "chatId": "5584999990000@s.whatsapp.net",
 *   "nome": "Maria Silva",
 *   "contato": "84999990000",
 *   "acao": 1,
 *   "data": "2026/04/15",
 *   "hora": "14:30",
 *   "cidade": "Mossoró",
 *   "procedimento": "Tratamento de Unhas"
 * }
 */
public class Entrada {

    @NotBlank(message = "chatId é obrigatório")
    public String chatId;

    public String nome;
    public String contato;
    public int acao;
    public String data;
    public String hora;
    public long dataHorario;
    public String cidade;
    public String procedimento;

 
    public void calcularDataHorario() {
        if (data != null && hora != null) {
            String[] partesData = data.split("/");
            String[] partesHora = hora.split(":");

            int ano    = Integer.parseInt(partesData[0]);
            int mes    = Integer.parseInt(partesData[1]);
            int dia    = Integer.parseInt(partesData[2]);
            int horaInt = Integer.parseInt(partesHora[0]);
            int minuto  = Integer.parseInt(partesHora[1]);

            dataHorario = (long) ano * 100000000L
                        + (long) mes * 1000000L
                        + (long) dia * 10000L
                        + (long) horaInt * 100L
                        + minuto;
        }
    }
}
