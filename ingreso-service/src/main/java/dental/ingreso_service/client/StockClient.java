package dental.ingreso_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "STOCK-SERVICE")
public interface StockClient {

    @PatchMapping("/api/stock/insumo/{insumoId}/cantidad")
    Object actualizarCantidad(@PathVariable("insumoId") Integer insumoId,
                              @RequestParam("cantidad") Integer cantidad);
}