package sistema;

public class Agenda {

    public void criarSessao() {

        if (vago() == false) {
            Sistema.gi().leitor.saida("Data indisponivel");
            System.out.println("Data indisponivel");
            return;
        }
        Sessao sessao = new Sessao();

        sessao.dataHorario = Sistema.gi().dados.dataHorario;
        sessao.procedimento = Sistema.gi().dados.procedimento;
        sessao.cidade = Sistema.gi().dados.cidade;
        sessao.completo = false;
        sessao.confirmado = false;

        Cliente cliente = Sistema.gi().banco.clientes.get(Sistema.gi().dados.chatId);

        if (cliente == null) {
            Sistema.gi().leitor.saida("Erro: cliente nao encontrado para criar sessao");
            System.out.println("Erro: cliente nao encontrado para criar sessao");
            return;
        }

            if (cliente.sessoes == null) {
                cliente.sessoes = new java.util.ArrayList<>();
            }
            cliente.sessoes.add(sessao.dataHorario);

        Sistema.gi().banco.sessoes.put(sessao.dataHorario, sessao);

        Sistema.gi().banco.db.commit();
        Sistema.gi().leitor.saida("Sessao criada com sucesso");
        System.out.println("Sessao criada com sucesso");
    }

    public void cancelarSessao() {
        if(Sistema.gi().banco.sessoes.containsKey(Sistema.gi().dados.dataHorario)) {
            Sessao sessao = Sistema.gi().banco.sessoes.get(Sistema.gi().dados.dataHorario);
            Sistema.gi().banco.sessoes.remove(Sistema.gi().dados.dataHorario);
            Cliente cliente = Sistema.gi().banco.clientes.get(Sistema.gi().dados.chatId);
            if (cliente != null && cliente.sessoes != null && sessao != null) {
                Sistema.gi().banco.clientes.put(cliente.chatId, cliente);
            }

            Sistema.gi().banco.db.commit();
            Sistema.gi().leitor.saida("Sessao cancelada com sucesso");
            System.out.println("Sessao cancelada com sucesso");
        } else {
            Sistema.gi().leitor.saida("Sessao nao encontrada");
            System.out.println("Sessao nao encontrada");
        }
    }

    public void statusSessao() {
        //entrega os elem   s equivalente a sessão desejada
    }
    
    public void confirmar() {
        //altera o status da sessão desejada para confirmado
    }

    private boolean vago() {
        int i;
        for (i = -20; vagoRepete(i) == true && i < 20; i++);

        if (i == 20) {
            return true;
        }
        return false;
    }

    private boolean vagoRepete(int i) {
        int aux = Sistema.gi().dados.dataHorario + i;
        if (Sistema.gi().banco.sessoes.containsKey(aux) == false) {
            return true;
        }
        return false;
    }

}
