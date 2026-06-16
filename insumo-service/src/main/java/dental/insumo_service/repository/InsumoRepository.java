package dental.insumo_service.repository;

import dental.insumo_service.model.Insumo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface InsumoRepository extends JpaRepository<Insumo, Integer> {

    List<Insumo> findByCategoria(String categoria);

    List<Insumo> findByFechaVencimientoBefore(LocalDate fecha);
}
