package dental.ingreso_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProveedorDTO {

    private Integer idProveedor;

    @NotBlank(message = "El nombre del proveedor es obligatorio")
    @Size(max = 255, message = "El nombre no puede superar los 255 caracteres")
    private String nombre;

    @NotBlank(message = "El RUT del proveedor es obligatorio")
    @Size(max = 20, message = "El RUT no puede superar los 20 caracteres")
    private String rut;

    @NotBlank(message = "El teléfono del proveedor es obligatorio")
    @Size(max = 20, message = "El teléfono no puede superar los 20 caracteres")
    private String telefono;

    @Email(message = "El email debe ser válido")
    @NotBlank(message = "El email del proveedor es obligatorio")
    @Size(max = 100, message = "El email no puede superar los 100 caracteres")
    private String email;

    private String direccion;
}
