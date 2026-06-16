package dental.Gestion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

// Este DTO es la respuesta enriquecida del endpoint GET /api/egresos/{id}/detalle
// En lugar de devolver solo los IDs (como hace el modelo Egreso),
// aquí incluyo el objeto InsumoDTO completo con todos sus datos traídos desde el otro microservicio
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EgresoDetalleDTO {

    private Integer idEgreso;
    private LocalDate fecha;
    private LocalTime hora;
    private Integer cantidad;
    private String motivo;
    private String observacion;

    // Aquí en lugar de guardar solo el insumoId, guardo el objeto InsumoDTO completo
    // que obtuve llamando al insumo-service a través de Feign
    private InsumoDTO insumo;

    // El destino por ahora solo lo incluyo como ID (no hay cliente Feign para ese servicio aquí)
    private Integer destinoId;
}
