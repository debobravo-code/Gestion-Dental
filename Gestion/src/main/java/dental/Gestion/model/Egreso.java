package dental.Gestion.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

// Le digo a Spring que esta clase representa una tabla en la base de datos
@Entity
// El nombre real de la tabla en la BD será "egresos"
@Table(name = "egresos")
// Lombok genera automáticamente getters, setters, equals, hashCode y toString
@Data
// Lombok genera un constructor vacío (necesario para JPA)
@NoArgsConstructor
// Lombok genera un constructor con todos los campos
@AllArgsConstructor
public class Egreso {

    // Esta es la clave primaria; la BD la genera automáticamente con autoincremento
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEgreso;

    // La fecha no puede ser nula en la BD; la asigno yo desde el service con LocalDate.now()
    @Column(nullable = false)
    private LocalDate fecha;

    // La hora tampoco puede ser nula; también la asigno desde el service con LocalTime.now()
    @Column(nullable = false)
    private LocalTime hora;

    // El usuario debe enviar la cantidad; si no la manda, @NotNull lanza un error de validación
    // @Positive garantiza que no acepte ceros ni negativos
    @NotNull(message = "La cantidad es obligatoria")
    @Positive(message = "La cantidad debe ser mayor a cero")
    @Column(nullable = false)
    private Integer cantidad;

    // @NotBlank valida que el motivo no llegue vacío ni con solo espacios
    @NotBlank(message = "El motivo es obligatorio")
    @Column(nullable = false)
    private String motivo;

    // La observación es opcional; puede llegar null sin problema
    private String observacion;

    // Guardo solo el ID del insumo (no el objeto completo) porque el insumo vive en otro microservicio
    @NotNull(message = "El id del insumo es obligatorio")
    @Column(nullable = false)
    private Integer insumoId;

    // Igual con destino: guardo solo el ID, el detalle lo consulta otro microservicio
    @NotNull(message = "El id del destino es obligatorio")
    @Column(nullable = false)
    private Integer destinoId;
}
