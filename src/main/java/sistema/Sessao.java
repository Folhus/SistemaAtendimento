package sistema;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "sessoes")
public class Sessao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    

    @Column(name = "data_horario", nullable = false)
    public long dataHorario;

    @Column(nullable = false)
    public String procedimento;

    @Column(nullable = false)
    public String cidade;

    @Column(nullable = false)
    public boolean confirmado = false;

    @Column(nullable = false)
    public boolean completo = false;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_chat_id", nullable = false)
    public Cliente cliente;
}
