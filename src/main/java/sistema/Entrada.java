package sistema;

import com.google.gson.Gson;

public class Entrada {
    public String nome;
    public String contato;
    public String chatId;
    public int acao;
    public String data;
    public String hora;
    public int dataHorario;
    public String cidade;
    public String procedimento;
    public boolean lido = false;

    public void calcularDataHorario() {
    if(data != null && hora != null) {
        String[] partesData = data.split("/");
        String[] partesHora = hora.split(":");

        int ano = Integer.parseInt(partesData[0]);
        int mes = Integer.parseInt(partesData[1]);
        int dia = Integer.parseInt(partesData[2]);
        int horaInt = Integer.parseInt(partesHora[0]);
        int minuto = Integer.parseInt(partesHora[1]);

        // Combinar em um único inteiro no formato AAAAMMDDHHMM
        dataHorario = ano * 100000000 + mes * 1000000 + dia * 10000 + horaInt * 100 + minuto;
    }
    }
    
    public String jsonString() {
        return new Gson().toJson(this);
    }
}
