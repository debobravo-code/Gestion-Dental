package dental.Gestion.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

// Feign se conecta al stock-service en el puerto 8086
@FeignClient(name = "STOCK-SERVICE")
public interface StockClient {

    // Llama a PATCH http://localhost:8086/api/stock/insumo/{insumoId}/cantidad?cantidad=X
    // Enviamos cantidad negativa para descontar (ej: -3 descuenta 3 unidades)
    @PatchMapping("/api/stock/insumo/{insumoId}/cantidad")
    void actualizarCantidad(@PathVariable Integer insumoId,
                            @RequestParam Integer cantidad);
}