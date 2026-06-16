package dental.ingreso_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "stock-service-ingreso", url = "http://localhost:8086")
public interface StockClient {

    @PatchMapping("/api/stock/insumo/{insumoId}/cantidad")
    void actualizarCantidad(@PathVariable Integer insumoId,
                            @RequestParam Integer cantidad);
}