package sistema;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * Teste básico que verifica se o contexto do Spring Boot sobe sem erros.
 * Para rodar os testes sem precisar do PostgreSQL, crie um
 * src/test/resources/application.properties com banco H2 (em memória).
 */
@SpringBootTest
@ActiveProfiles("test")
public class AppTest {

    @Test
    void contextLoads() {
        // Se o Spring Boot inicializar sem exceção, o teste passa.
    }

    @Test
    void calcularDataHorarioFunciona() {
        Entrada e = new Entrada();
        e.data = "2026/04/15";
        e.hora = "14:30";
        e.calcularDataHorario();
        assert e.dataHorario == 202604151430L : "dataHorario calculado incorretamente";
    }

    @Test
    void saidaOkEErroFuncionam() {
        Saida ok = Saida.ok("sucesso");
        assert ok.sucesso;
        assert ok.mensagem.equals("sucesso");

        Saida erro = Saida.erro("falha");
        assert !erro.sucesso;
        assert erro.mensagem.equals("falha");
    }
}
