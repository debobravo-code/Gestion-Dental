package dental.Gestion.client;

import dental.Gestion.dto.InsumoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Le digo a Feign que esta interfaz representa una conexión al microservicio "insumo-service"
// Feign se encarga de hacer la llamada HTTP por mí; yo solo defino el método como si fuera local
// "name" es el nombre lógico del servicio y "url" es donde está corriendo
@FeignClient(name = "INSUMO-SERVICE")
public interface InsumoClient {

    // Cuando llamo a este método, Feign hace un GET a http://localhost:8081/api/insumos/{id}
    // La respuesta JSON que devuelve el otro microservicio la convierte automáticamente en un objeto InsumoDTO
    // @PathVariable toma el Integer id y lo pone en la URL en lugar de {id}
    @GetMapping("/api/insumos/{id}")
    InsumoDTO obtenerInsumoPorId(@PathVariable Integer id);
}
