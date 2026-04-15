package sistema;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class ClienteService {

    private final ClienteRepository clienteRepo;

    public ClienteService(ClienteRepository clienteRepo) {
        this.clienteRepo = clienteRepo;
    }


    public boolean existe(String chatId) {
        return clienteRepo.existsById(chatId);
    }


    @Transactional
    public Saida buscarOuCriar(Entrada dados) {
        Optional<Cliente> clienteOpt = clienteRepo.findByChatId(dados.chatId);

        if (clienteOpt.isPresent()) {
            Saida saida = Saida.ok("Cliente reconhecido");
            saida.cliente = Saida.ClienteDTO.de(clienteOpt.get());
            return saida;
        }

        // Cliente novo — cadastra automaticamente
        Cliente cliente = new Cliente();
        cliente.chatId  = dados.chatId;
        cliente.nome    = dados.nome != null ? dados.nome : "Desconhecido";
        cliente.contato = dados.contato != null ? dados.contato : dados.chatId;

        clienteRepo.save(cliente);

        Saida saida = Saida.ok("Cliente cadastrado com sucesso");
        saida.cliente = Saida.ClienteDTO.de(cliente);
        return saida;
    }


    @Transactional
    public Saida atualizar(String chatId, Entrada dados) {
        Optional<Cliente> clienteOpt = clienteRepo.findByChatId(chatId);

        if (clienteOpt.isEmpty()) {
            return Saida.erro("Cliente nao encontrado");
        }

        Cliente cliente = clienteOpt.get();

        if (dados.nome != null)    cliente.nome = dados.nome;
        if (dados.contato != null) cliente.contato = dados.contato;

        clienteRepo.save(cliente);

        Saida saida = Saida.ok("Dados atualizados com sucesso");
        saida.cliente = Saida.ClienteDTO.de(cliente);
        return saida;
    }


    @Transactional(readOnly = true)
    public Saida buscar(String chatId) {
        Optional<Cliente> clienteOpt = clienteRepo.findByChatId(chatId);

        if (clienteOpt.isEmpty()) {
            return Saida.erro("Cliente nao encontrado");
        }

        Saida saida = Saida.ok("Ok");
        saida.cliente = Saida.ClienteDTO.de(clienteOpt.get());
        return saida;
    }
}
