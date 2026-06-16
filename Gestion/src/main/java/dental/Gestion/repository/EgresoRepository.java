package dental.Gestion.repository;

import dental.Gestion.model.Egreso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EgresoRepository extends JpaRepository<Egreso, Integer> {

    List<Egreso> findByInsumoId(Integer insumoId);

    List<Egreso> findByDestinoId(Integer destinoId);

    List<Egreso> findByFecha(LocalDate fecha);

    List<Egreso> findByFechaBetween(LocalDate inicio, LocalDate fin);

  
}