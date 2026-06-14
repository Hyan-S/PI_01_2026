package pi.Senai.Senai.infra;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. Regras de Negocio
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, String>> handleResponseStatusException(ResponseStatusException ex) {
        return buildResponse(ex.getReason(), HttpStatus.valueOf(ex.getStatusCode().value()));
    }

    // 2. Banco de Dados
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        return buildResponse("Operação negada: Registro possui vínculos no sistema e não pode ser alterado ou excluído.", HttpStatus.CONFLICT);
    }

    // 3. IllegalArgumentException e IllegalStateException
    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    public ResponseEntity<Map<String, String>> handleIllegalArguments(RuntimeException ex) {
        // Geralmente sao erros de validacao ou fluxo que os devs colocam nos Services
        return buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // 4. A REDE DE PROTECAO FINAL (Pega-Tudo)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleAllUncaughtException(Exception ex) {
        // IMPRIME NO CONSOLE DO BACKEND PARA VOCES CONSEGUIREM DEBUGAR
        ex.printStackTrace(); 
        
        // DEVOLVE PARA O FRONT-END UMA MENSAGEM LIMPA, SEM DERRUBAR A SESSAO
        return buildResponse("Ocorreu um erro interno inesperado. Contate o administrador.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Metodo auxiliar para nao repetir codigo de montagem de JSON
    private ResponseEntity<Map<String, String>> buildResponse(String message, HttpStatus status) {
        Map<String, String> response = new HashMap<>();
        response.put("message", message != null ? message : "Erro desconhecido.");
        return ResponseEntity.status(status).body(response);
    }
}