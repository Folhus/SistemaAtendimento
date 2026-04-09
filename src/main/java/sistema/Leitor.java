package sistema;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;

public class Leitor {

    private Gson gson = new Gson();

    private Saida mensagem;

    public Entrada dados;

    public void ler() {

        try {
            BufferedReader input = new BufferedReader(new FileReader("entrada.json"));
            Sistema.gi().dados = gson.fromJson(input, Entrada.class);
            input.close();
        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo: " + e.getMessage());
            
            if (Sistema.gi().dados == null) {
                Sistema.gi().dados = new Entrada();
            }
        }
    }

    public void reescrever() {
        try {
            Sistema.gi().dados.lido = true;
            
            FileWriter escritor = new FileWriter("entrada.json");
            escritor.write(Sistema.gi().dados.jsonString());
            escritor.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saida(String msg) {
        try {
            moldeSaida();
            mensagem.mensagem = msg;

            FileWriter escritor = new FileWriter("saida.json");


            escritor.write(gson.toJson(mensagem));
            escritor.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void moldeSaida() {
        try {
            BufferedReader input = new BufferedReader(new FileReader("saida.json"));

            com.google.gson.JsonElement je = gson.fromJson(input, com.google.gson.JsonElement.class);
            input.close();

            if (je != null && je.isJsonObject()) {
                com.google.gson.JsonObject jo = je.getAsJsonObject();
                if (jo.has("statusSessao") && jo.get("statusSessao").isJsonObject()) {
                    com.google.gson.JsonObject ss = jo.getAsJsonObject("statusSessao");
                    if (ss.has("cliente") && ss.get("cliente").isJsonPrimitive()) {
                        com.google.gson.JsonObject clienteObj = new com.google.gson.JsonObject();
                        clienteObj.addProperty("nome", "");
                        clienteObj.addProperty("contato", "");
                        clienteObj.addProperty("chatId", "");
                        ss.add("cliente", clienteObj);
                    }
                }
                mensagem = gson.fromJson(jo, Saida.class);
            } else {
                mensagem = new Saida();
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo: " + e.getMessage());
            mensagem = new Saida();
        }
    }
}
