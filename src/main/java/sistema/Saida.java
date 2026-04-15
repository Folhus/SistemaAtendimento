package sistema;

/**
 * DTO de resposta enviado de volta ao bot ou site após cada operação.
 * O Spring converte automaticamente para JSON.
 *
 * Exemplo de resposta:
 * {
 *   "sucesso": true,
 *   "mensagem": "Sessao criada com sucesso",
 *   "sessao": { ... }
 * }
 */
public class Saida {

    public boolean sucesso;
    public String mensagem;

    // Preenchido apenas quando a operação retorna dados de sessão
    public SessaoDTO sessao;

    // Preenchido apenas quando a operação retorna dados de cliente
    public ClienteDTO cliente;

    // ----------------------------------------------------------------
    // Construtores estáticos para facilitar a criação de respostas
    // ----------------------------------------------------------------

    public static Saida ok(String mensagem) {
        Saida s = new Saida();
        s.sucesso = true;
        s.mensagem = mensagem;
        return s;
    }

    public static Saida erro(String mensagem) {
        Saida s = new Saida();
        s.sucesso = false;
        s.mensagem = mensagem;
        return s;
    }

    // ----------------------------------------------------------------
    // DTOs internos — evitam expor entidades JPA diretamente na API
    // ----------------------------------------------------------------

    public static class SessaoDTO {
        public Long id;
        public long dataHorario;
        public String dataFormatada;
        public String procedimento;
        public String cidade;
        public boolean confirmado;
        public boolean completo;

        public static SessaoDTO de(Sessao sessao) {
            SessaoDTO dto = new SessaoDTO();
            dto.id = sessao.id;
            dto.dataHorario = sessao.dataHorario;
            dto.dataFormatada = formatarDataHorario(sessao.dataHorario);
            dto.procedimento = sessao.procedimento;
            dto.cidade = sessao.cidade;
            dto.confirmado = sessao.confirmado;
            dto.completo = sessao.completo;
            return dto;
        }

        private static String formatarDataHorario(long dh) {
            String s = String.valueOf(dh);
            // Formato: AAAAMMDDHHMM → DD/MM/AAAA HH:MM
            return s.substring(6, 8) + "/" + s.substring(4, 6) + "/" + s.substring(0, 4)
                    + " " + s.substring(8, 10) + ":" + s.substring(10, 12);
        }
    }

    public static class ClienteDTO {
        public String chatId;
        public String nome;
        public String contato;

        public static ClienteDTO de(Cliente cliente) {
            ClienteDTO dto = new ClienteDTO();
            dto.chatId = cliente.chatId;
            dto.nome = cliente.nome;
            dto.contato = cliente.contato;
            return dto;
        }
    }
}
