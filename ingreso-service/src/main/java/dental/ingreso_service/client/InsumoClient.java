package dental.ingreso_service.client;

import dental.ingreso_service.dto.InsumoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "INSUMO-SERVICE")
public interface InsumoClient {

    @GetMapping("/api/insumos/{id}")
    InsumoDTO obtenerInsumoPorId(@PathVariable Integer id);
}
