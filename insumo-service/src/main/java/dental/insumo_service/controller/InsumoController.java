package dental.insumo_service.controller;

import dental.insumo_service.model.Insumo;
import dental.insumo_service.service.InsumoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/insumos")
@RequiredArgsConstructor
public class InsumoController {

    private final InsumoService insumoService;

    @GetMapping
    public ResponseEntity<?> listarInsumos() {
        return ResponseEntity.ok(insumoService.listarInsumos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(insumoService.buscarPorId(id));
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<?> buscarPorCategoria(@PathVariable String categoria) {
        return ResponseEntity.ok(insumoService.buscarPorCategoria(categoria));
    }

    @GetMapping("/proximos-vencer")
    public ResponseEntity<?> buscarProximosAVencer() {
        return ResponseEntity.ok(insumoService.buscarProximosAVencer());
    }

    @PostMapping
    public ResponseEntity<?> guardarInsumo(@Valid @RequestBody Insumo insumo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(insumoService.guardarInsumo(insumo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarInsumo(@PathVariable Integer id,
                                               @Valid @RequestBody Insumo insumo) {
        return ResponseEntity.ok(insumoService.actualizarInsumo(id, insumo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarInsumo(@PathVariable Integer id) {
        insumoService.eliminarInsumo(id);
        return ResponseEntity.noContent().build();
    }
}
