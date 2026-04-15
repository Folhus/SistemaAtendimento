package sistema;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;


 //todos os erros são convertidos para o formato Saida (JSON).
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Erros de validação (@NotBlank, @NotNull, etc.)
     * Ex: chatId não foi enviado no corpo da requisição.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Saida> handleValidacao(MethodArgumentNotValidException ex) {
        String erros = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Saida.erro("Dados invalidos: " + erros));
    }

    /**
     * Qualquer outro erro inesperado.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Saida> handleGeral(Exception ex) {
        ex.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Saida.erro("Erro interno: " + ex.getMessage()));
    }
}
