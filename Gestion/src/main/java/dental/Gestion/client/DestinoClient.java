package dental.Gestion.client; // agregue un comntaro

import dental.Gestion.dto.DestinoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Feign se conecta al destino-service en el puerto 8083
// Igual que InsumoClient pero apuntando al servicio de destinos
@FeignClient(name = "DESTINO-SERVICE")
public interface DestinoClient {

    // Cuando llamo a este método, Feign hace GET http://localhost:8083/api/destinos/{id}
    // Si el destino no existe allá, Feign lanza una excepción que capturo en el service
    @GetMapping("/api/destinos/{id}")
    DestinoDTO obtenerDestinoPorId(@PathVariable Integer id);
}
