package sistema;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// ============================================================
// Repositório de Clientes
// O Spring gera automaticamente os métodos de busca, salvamento, etc.
// ============================================================
@Repository
interface ClienteRepository extends JpaRepository<Cliente, String> {
    Optional<Cliente> findByChatId(String chatId);
    Optional<Cliente> findByContato(String contato);
}

// ============================================================
// Repositório de Sessões
// ============================================================
@Repository
interface SessaoRepository extends JpaRepository<Sessao, Long> {

    /**
     * Busca todas as sessões de um cliente pelo chatId.
     */
    List<Sessao> findByClienteChatId(String chatId);

    /**
     * Verifica se já existe alguma sessão em um intervalo de dataHorario.
     * Usado para checar disponibilidade antes de agendar.
     */
    @Query("SELECT COUNT(s) > 0 FROM Sessao s WHERE s.dataHorario BETWEEN :inicio AND :fim")
    boolean existeConflito(@Param("inicio") long inicio, @Param("fim") long fim);

    Optional<Sessao> findByDataHorario(long dataHorario);
}

// ============================================================
// Repositório de Procedimentos
// ============================================================
@Repository
interface ProcedimentoRepository extends JpaRepository<Procedimento, Long> {
    Optional<Procedimento> findByNome(String nome);
}

// ============================================================
// Repositório de Atendimentos
// ============================================================
@Repository
interface AtendimentoRepository extends JpaRepository<Atendimento, Long> {
    Optional<Atendimento> findBySessaoId(Long sessaoId);
    List<Atendimento> findBySessaoClienteChatId(String chatId);
}
