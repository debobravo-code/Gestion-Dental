package dental.ingreso_service.controller;

import dental.ingreso_service.model.Ingreso;
import dental.ingreso_service.service.IngresoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ingresos")
@RequiredArgsConstructor
public class IngresoController {

    private final IngresoService ingresoService;

    @GetMapping
    public ResponseEntity<?> listarIngresos() {
        return ResponseEntity.ok(ingresoService.listarIngresos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(ingresoService.buscarPorId(id));
    }

    @GetMapping("/{id}/detalle")
    public ResponseEntity<?> buscarDetallePorId(@PathVariable Integer id) {
        return ResponseEntity.ok(ingresoService.buscarDetallePorId(id));
    }

    @GetMapping("/insumo/{insumoId}")
    public ResponseEntity<?> buscarPorInsumoId(@PathVariable Integer insumoId) {
        return ResponseEntity.ok(ingresoService.buscarPorInsumoId(insumoId));
    }

    @GetMapping("/proveedor/{proveedorId}")
    public ResponseEntity<?> buscarPorProveedorId(@PathVariable Integer proveedorId) {
        return ResponseEntity.ok(ingresoService.buscarPorProveedorId(proveedorId));
    }

    @PostMapping
    public ResponseEntity<?> guardarIngreso(@Valid @RequestBody Ingreso ingreso) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ingresoService.guardarIngreso(ingreso));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarIngreso(@PathVariable Integer id,
                                                @Valid @RequestBody Ingreso ingreso) {
        return ResponseEntity.ok(ingresoService.actualizarIngreso(id, ingreso));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarIngreso(@PathVariable Integer id) {
        ingresoService.eliminarIngreso(id);
        return ResponseEntity.noContent().build();
    }
}
