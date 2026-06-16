package dental.ingreso_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InsumoDTO {

    private Integer idInsumo;

    @NotBlank(message = "El nombre del insumo es obligatorio")
    @Size(max = 255, message = "El nombre no puede superar los 255 caracteres")
    private String nombre;

    @NotBlank(message = "La categoría del insumo es obligatoria")
    @Size(max = 100, message = "La categoría no puede superar los 100 caracteres")
    private String categoria;

    @NotBlank(message = "La unidad de medida es obligatoria")
    @Size(max = 50, message = "La unidad de medida no puede superar los 50 caracteres")
    private String unidadMedida;

    private String fechaVencimiento;

    @NotNull(message = "El stock mínimo es obligatorio")
    @Positive(message = "El stock mínimo debe ser mayor a cero")
    private Integer stockMinimo;
}
