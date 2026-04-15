package sistema;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "atendimentos")
public class Atendimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;


    @OneToOne
    @JoinColumn(name = "sessao_id", nullable = false)
    public Sessao sessao;

    @Column(columnDefinition = "TEXT")
    public String observacoes;

    @Column(columnDefinition = "TEXT")
    public String evolucao;

    @Column
    public String retorno; // data de retorno recomendada
}
