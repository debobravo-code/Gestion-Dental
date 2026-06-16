package dental.Gestion.controller;

import dental.Gestion.model.Egreso;
import dental.Gestion.service.EgresoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Le digo a Spring que esta clase es un controlador REST; responde y devuelve JSON automáticamente
@RestController
// Todas las rutas de esta clase empiezan con /api/egresos
@RequestMapping("/api/egresos")
// Lombok genera el constructor; Spring inyecta el EgresoService automáticamente
@RequiredArgsConstructor
public class EgresoController {

    // EgresoService es el objeto que tiene la lógica de negocio; lo uso en cada endpoint
    private final EgresoService egresoService;

    // GET /api/egresos → devuelve todos los egresos
    // ResponseEntity<?> me permite controlar el código HTTP y el cuerpo de la respuesta
    // El ? significa que el cuerpo puede ser cualquier tipo (lista, error, etc.)
    @GetMapping
    public ResponseEntity<?> listarEgresos() {

        return ResponseEntity.ok(
                egresoService.listarEgresos());
    }

    // GET /api/egresos/{id} → busca un egreso por su ID
    // @PathVariable toma el valor {id} de la URL y lo convierte en un Integer
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {

        return ResponseEntity.ok(
                egresoService.buscarPorId(id));
    }

    // GET /api/egresos/insumo/{insumoId} → devuelve todos los egresos que usan ese insumo
    @GetMapping("/insumo/{insumoId}")
    public ResponseEntity<?> buscarPorInsumoId(
            @PathVariable Integer insumoId) {

        return ResponseEntity.ok(
                egresoService.buscarPorInsumoId(insumoId));
    }

    // GET /api/egresos/destino/{destinoId} → devuelve todos los egresos hacia ese destino
    @GetMapping("/destino/{destinoId}")
    public ResponseEntity<?> buscarPorDestinoId(
            @PathVariable Integer destinoId) {

        return ResponseEntity.ok(
                egresoService.buscarPorDestinoId(destinoId));
    }

    // GET /api/egresos/{id}/detalle → devuelve el egreso enriquecido con los datos del insumo
    // (llama al insumo-service vía Feign internamente en el service)
    @GetMapping("/{id}/detalle")
    public ResponseEntity<?> buscarDetallePorId(
            @PathVariable Integer id) {

        return ResponseEntity.ok(
                egresoService.buscarDetallePorId(id));
    }

    // POST /api/egresos → crea un nuevo egreso
    // @RequestBody convierte el JSON que manda el cliente en un objeto de tipo Egreso (viene del modelo)
    // @Valid activa las validaciones definidas en Egreso (@NotNull, @NotBlank, etc.)
    //   Si alguna validación falla, Spring lanza MethodArgumentNotValidException
    //   y el GlobalExceptionHandler la captura devolviendo HTTP 400 con los errores
    // ResponseEntity.status(HttpStatus.CREATED) devuelve HTTP 201 (recurso creado exitosamente)
    // .body(...) pone el egreso guardado (con su ID ya asignado) como cuerpo de la respuesta JSON
    @PostMapping
    public ResponseEntity<?> guardarEgreso(
            @Valid @RequestBody Egreso egreso) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(egresoService.guardarEgreso(egreso));
    }

    // PUT /api/egresos/{id} → actualiza un egreso existente
    // @PathVariable trae el ID desde la URL
    // @Valid @RequestBody trae y valida los nuevos datos del egreso desde el cuerpo JSON
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarEgreso(
            @PathVariable Integer id,
            @Valid @RequestBody Egreso egreso) {

        return ResponseEntity.ok(
                egresoService.actualizarEgreso(id, egreso));
    }

    // DELETE /api/egresos/{id} → elimina el egreso con ese ID
    // El service no devuelve nada (void), por eso no paso nada al body
    // ResponseEntity.noContent().build() devuelve HTTP 204 (éxito sin contenido en el cuerpo)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarEgreso(
            @PathVariable Integer id) {

        egresoService.eliminarEgreso(id);

        return ResponseEntity.noContent().build();
    }
}
