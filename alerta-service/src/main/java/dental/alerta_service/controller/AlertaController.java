package dental.alerta_service.controller;

import dental.alerta_service.model.Alerta;
import dental.alerta_service.service.AlertaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/alertas")
@RequiredArgsConstructor
public class AlertaController {

    private final AlertaService alertaService;

    @GetMapping
    public ResponseEntity<?> listarAlertas() {
        return ResponseEntity.ok(alertaService.listarAlertas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(alertaService.buscarPorId(id));
    }

    @GetMapping("/no-revisadas")
    public ResponseEntity<?> listarNoRevisadas() {
        return ResponseEntity.ok(alertaService.listarNoRevisadas());
    }

    @GetMapping("/insumo/{insumoId}")
    public ResponseEntity<?> buscarPorInsumoId(@PathVariable Integer insumoId) {
        return ResponseEntity.ok(alertaService.buscarPorInsumoId(insumoId));
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<?> buscarPorTipo(@PathVariable String tipo) {
        return ResponseEntity.ok(alertaService.buscarPorTipo(tipo));
    }

    @PostMapping
    public ResponseEntity<?> guardarAlerta(@Valid @RequestBody Alerta alerta) {
        return ResponseEntity.status(HttpStatus.CREATED).body(alertaService.guardarAlerta(alerta));
    }

    @PatchMapping("/{id}/revisar")
    public ResponseEntity<?> marcarComoRevisada(@PathVariable Integer id) {
        return ResponseEntity.ok(alertaService.marcarComoRevisada(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarAlerta(@PathVariable Integer id) {
        alertaService.eliminarAlerta(id);
        return ResponseEntity.noContent().build();
    }
}
