package sistema;

import java.io.Serializable;

public class Sessao implements Serializable {
    public int dataHorario;
    public Cliente cliente;
    public String procedimento; //string para facilitar a serialização
    public String cidade;
    public boolean confirmado = false;
    public boolean completo = false;
}
