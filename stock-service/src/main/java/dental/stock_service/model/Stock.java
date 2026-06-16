package dental.stock_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "stock")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idStock;

    @NotNull(message = "El id del insumo es obligatorio")
    @Column(nullable = false, unique = true)
    private Integer insumoId;

    @NotNull(message = "La cantidad actual es obligatoria")
    @PositiveOrZero(message = "La cantidad no puede ser negativa")
    @Column(nullable = false)
    private Integer cantidadActual;

    @Column(nullable = false)
    private Integer cantidadMinima;
}
