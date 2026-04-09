package sistema;

public class Sistema
{
    private static Sistema sistema;

    public Agenda agenda;

    public Entrada dados;

    public Banco banco;

    public Leitor leitor;

    public Impressora impressora;

    private Sistema() {
        this.dados = new Entrada();
        this.leitor = new Leitor();
        this.banco = new Banco();
        this.agenda = new Agenda();
        this.impressora = new Impressora();
    }

    public static Sistema gi() {
        if(sistema == null) {
            sistema = new Sistema();
        }
        return sistema;
    }

    public boolean localizarCliente() {
        if (dados == null || (dados.contato == null && dados.chatId == null)) {
            return false;
        }
        
        if (dados.chatId != null && banco.clientes.containsKey(dados.chatId)) {
            return true;
        }
        return false;
    }

    public void addCliente() {
        Cliente c = new Cliente();

        c.nome = (dados.nome != null) ? dados.nome : "Desconhecido";
        c.contato = dados.contato;
        c.chatId = dados.chatId;

        System.out.println(c.nome + " " + c.contato + " " + c.chatId);

        banco.clientes.put(c.chatId, c);

        banco.db.commit();
        System.out.println("Cliente adicionado.");
    }

    public static void main( String[] args )
    {
        Sistema s = gi();
        
        while(true) {
            try {
                s.leitor.ler();

                if (!s.dados.lido) {
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        s.banco.abrir();

        if(s.localizarCliente()) {
            System.out.println("Cliente Reconhecido.");
        } else {
            System.out.println("Cliente Desconhecido");
            s.addCliente();
        }

        s.dados.calcularDataHorario();
        
        switch (s.dados.acao) {
            case 1:
                System.out.println("Fazer Agendamento");
                s.agenda.criarSessao();
                break;
            case 2:
                System.out.println("Cancelar Agendamento");
                s.agenda.cancelarSessao();
                break;
            case 3:
                System.out.println("Consultar Agendamento");
                s.agenda.statusSessao();
                break;
            case 4:
                System.out.println("Confirmar Agendamento");
                s.agenda.confirmar();
                break;
            case 444:
                System.out.println("Criar log de banco");
                s.impressora.imprimirBanco();
                break;
        }

        s.leitor.reescrever();

        s.banco.db.close();
        
        main(null);
    }
}


//Sistema.gi().leitor.saida("");