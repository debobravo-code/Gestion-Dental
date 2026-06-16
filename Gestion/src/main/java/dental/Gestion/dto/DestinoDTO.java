package dental.Gestion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO que recibe la respuesta del destino-service via Feign
// No va a la BD, solo transporta los datos del destino para validar que existe
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DestinoDTO {

    private Integer idDestino;
    private String nombre;
    private String tipo;
    private String descripcion;
}
