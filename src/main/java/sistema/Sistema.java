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
        Entrada d = Sistema.gi().dados;
        if (d == null || (d.contato == null && d.chatId == null)) {
            return false;
        }
        
        if (d.chatId != null && banco.clientes.containsKey(d.chatId)) {
            return true;
        }
        return false;
    }

    public void addCliente() {
        Cliente c = new Cliente();

        c.nome = (Sistema.gi().dados.nome != null) ? Sistema.gi().dados.nome : "Desconhecido";
        c.contato = Sistema.gi().dados.contato;
        c.chatId = Sistema.gi().dados.chatId;

        System.out.println(c.nome + " " + c.contato + " " + c.chatId);

        Sistema.gi().banco.clientes.put(c.chatId, c);

        Sistema.gi().banco.db.commit();
        System.out.println("Cliente adicionado.");
    }

    public static void main( String[] args )
    {
        do {
            try {
                Sistema.gi().leitor.ler();

                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }while(Sistema.gi().dados.lido==true);

        Sistema.gi().banco.abrir();

        if(Sistema.gi().localizarCliente()) {
            System.out.println("Cliente Reconhecido.");
        } else {
            System.out.println("Cliente Desconhecido");
            Sistema.gi().addCliente();
        }

        Sistema.gi().dados.calcularDataHorario();
        
        switch (Sistema.gi().dados.acao) {
            case 1:
                System.out.println("Fazer Agendamento");
                Sistema.gi().agenda.criarSessao();
                break;
            case 2:
                System.out.println("Cancelar Agendamento");
                Sistema.gi().agenda.cancelarSessao();
                break;
            case 3:
                System.out.println("Consultar Agendamento");
                Sistema.gi().agenda.statusSessao();
                break;
            case 4:
                System.out.println("Confirmar Agendamento");
                Sistema.gi().agenda.confirmar();
                break;
            case 444:
                System.out.println("Criar log de banco");
                Sistema.gi().impressora.imprimirBanco();
                break;
        }

        Sistema.gi().leitor.reescrever();

        Sistema.gi().banco.db.close();
        
        Sistema.gi().main(null);
    }
}


//Sistema.gi().leitor.saida("");