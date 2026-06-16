package dental.ingreso_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngresoDetalleDTO {

    private Integer idIngreso;
    private LocalDate fecha;
    private LocalTime hora;
    private Integer cantidad;
    private String numeroFactura;
    private String observacion;

    private InsumoDTO insumo;
    private ProveedorDTO proveedor;
}
