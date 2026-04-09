package sistema;

import java.io.Serializable;
import java.util.ArrayList;

public class Cliente implements Serializable {
    public String nome;
    public String contato;
    public String chatId;
    public ArrayList<Integer> sessoes = new ArrayList<>();
}
