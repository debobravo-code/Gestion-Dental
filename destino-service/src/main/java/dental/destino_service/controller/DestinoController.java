package dental.destino_service.controller;

import dental.destino_service.model.Destino;
import dental.destino_service.service.DestinoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/destinos")
@RequiredArgsConstructor
public class DestinoController {

    private final DestinoService destinoService;

    @GetMapping
    public ResponseEntity<?> listarDestinos() {
        return ResponseEntity.ok(destinoService.listarDestinos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(destinoService.buscarPorId(id));
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<?> buscarPorTipo(@PathVariable String tipo) {
        return ResponseEntity.ok(destinoService.buscarPorTipo(tipo));
    }

    @GetMapping("/activos")
    public ResponseEntity<?> listarActivos() {
        return ResponseEntity.ok(destinoService.listarActivos());
    }

    @PostMapping
    public ResponseEntity<?> guardarDestino(@Valid @RequestBody Destino destino) {
        return ResponseEntity.status(HttpStatus.CREATED).body(destinoService.guardarDestino(destino));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarDestino(@PathVariable Integer id,
                                                @Valid @RequestBody Destino destino) {
        return ResponseEntity.ok(destinoService.actualizarDestino(id, destino));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarDestino(@PathVariable Integer id) {
        destinoService.eliminarDestino(id);
        return ResponseEntity.noContent().build();
    }
}
