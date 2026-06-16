package dental.ingreso_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "ingresos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ingreso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idIngreso;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false)
    private LocalTime hora;

    @NotNull(message = "La cantidad es obligatoria")
    @Positive(message = "La cantidad debe ser mayor a cero")
    @Column(nullable = false)
    private Integer cantidad;

    @NotBlank(message = "El número de factura es obligatorio")
    @Column(nullable = false)
    private String numeroFactura;

    private String observacion;

    @NotNull(message = "El id del insumo es obligatorio")
    @Column(nullable = false)
    private Integer insumoId;

    @NotNull(message = "El id del proveedor es obligatorio")
    @Column(nullable = false)
    private Integer proveedorId;
}
