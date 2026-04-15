package sistema;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name = "clientes")
public class Cliente {

    /**
     * chatId é o identificador único do cliente (vindo do WhatsApp via bot Node.js).
     * Usado como chave primária pois já é único por natureza.
     */
    @Id
    @Column(name = "chat_id", nullable = false, unique = true)
    public String chatId;

    @Column(nullable = false)
    public String nome;

    @Column(nullable = false)
    public String contato;

    /**
     * Lista de sessões do cliente.
     * OneToMany: um cliente pode ter várias sessões.
     * cascade = PERSIST/MERGE: salvar/atualizar o cliente afeta as sessões.
     * orphanRemoval: remover da lista apaga do banco.
     */
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Sessao> sessoes = new ArrayList<>();
}
