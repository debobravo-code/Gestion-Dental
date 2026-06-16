package dental.stock_service.controller;

import dental.stock_service.model.Stock;
import dental.stock_service.service.StockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stock")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @GetMapping
    public ResponseEntity<?> listarStock() {
        return ResponseEntity.ok(stockService.listarStock());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(stockService.buscarPorId(id));
    }

    @GetMapping("/insumo/{insumoId}")
    public ResponseEntity<?> buscarPorInsumoId(@PathVariable Integer insumoId) {
        return ResponseEntity.ok(stockService.buscarPorInsumoId(insumoId));
    }

    @GetMapping("/bajo")
    public ResponseEntity<?> listarStockBajo() {
        return ResponseEntity.ok(stockService.listarStockBajo());
    }

    @PostMapping
    public ResponseEntity<?> guardarStock(@Valid @RequestBody Stock stock) {
        return ResponseEntity.status(HttpStatus.CREATED).body(stockService.guardarStock(stock));
    }

    @PatchMapping("/insumo/{insumoId}/cantidad")
    public ResponseEntity<?> actualizarCantidad(@PathVariable Integer insumoId,
                                                  @RequestParam Integer cantidad) {
        return ResponseEntity.ok(stockService.actualizarCantidad(insumoId, cantidad));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarStock(@PathVariable Integer id) {
        stockService.eliminarStock(id);
        return ResponseEntity.noContent().build();
    }
}
