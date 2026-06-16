package dental.Gestion.exception;

import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

// Esta clase intercepta todas las excepciones que se lanzan en cualquier controlador
// En vez de que Spring devuelva un error genérico, yo controlo qué JSON le llega al cliente
// @RestControllerAdvice combina @ControllerAdvice + @ResponseBody: aplica a todos los controllers
// y devuelve automáticamente JSON en la respuesta
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Este método se activa cuando en el service lanzo: throw new RecursoNoEncontradoException(...)
    // El parámetro "ex" es el objeto de tipo RecursoNoEncontradoException que fue lanzado;
    // lo recibo aquí para poder leer su mensaje con ex.getMessage()
    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<?> manejarRecursoNoEncontrado(RecursoNoEncontradoException ex) {

        // Creo un mapa (clave-valor) que se convertirá en JSON: { "error": "mensaje..." }
        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("error", ex.getMessage());

        // Devuelvo HTTP 404 Not Found con el mapa como cuerpo de la respuesta
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
    }

    // Este método captura FeignException.NotFound que no fue convertida en RecursoNoEncontradoException
    // Es una red de seguridad por si algún método olvidó manejar el 404 de Feign manualmente
    @ExceptionHandler(FeignException.NotFound.class)
    public ResponseEntity<?> manejarFeignNotFound(FeignException.NotFound ex) {

        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("error", "Recurso no encontrado en microservicio externo: " + ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
    }

    // Captura cualquier FeignException que no sea 404 (servicio caído, timeout, 500 remoto, etc.)
    // Devuelve 503 Service Unavailable para indicar que un microservicio dependiente falló
    @ExceptionHandler(FeignException.class)
    public ResponseEntity<?> manejarFeignException(FeignException ex) {

        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("error", "Error de comunicación con microservicio externo.");
        respuesta.put("detalle", "Código HTTP recibido: " + ex.status());

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(respuesta);
    }

    // Este método se activa cuando falla alguna validación de @Valid en el controller
    // Por ejemplo si llega cantidad null o motivo vacío, Spring lanza MethodArgumentNotValidException
    // El parámetro "ex" contiene todos los errores de validación que fallaron
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> manejarValidaciones(MethodArgumentNotValidException ex) {

        // Creo un mapa donde la clave es el nombre del campo y el valor es el mensaje de error
        // Resultado JSON ejemplo: { "cantidad": "La cantidad es obligatoria", "motivo": "El motivo es obligatorio" }
        Map<String, String> errores = new HashMap<>();

        // Recorro cada error de campo que vino en la excepción y lo agrego al mapa
        // error.getField() → nombre del campo que falló (ej: "cantidad")
        // error.getDefaultMessage() → mensaje definido en la anotación (ej: "La cantidad es obligatoria")
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errores.put(error.getField(), error.getDefaultMessage())
        );

        // Devuelvo HTTP 400 Bad Request con todos los errores de validación
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
    }

    // Este método captura cualquier otra excepción inesperada que no sea de los tipos anteriores
    // Es una red de seguridad: si algo explota sin que yo lo haya previsto, devuelvo un 500 genérico
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> manejarErrorGeneral(Exception ex) {

        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("error", "Error interno del servidor");

        // Devuelvo HTTP 500 Internal Server Error con un mensaje genérico (no expongo la causa real)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
    }
}
