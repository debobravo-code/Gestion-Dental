package dental.reporte_service.repository;

import dental.reporte_service.model.Reporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReporteRepository extends JpaRepository<Reporte, Integer> {

    List<Reporte> findByTipoReporte(String tipoReporte);

    List<Reporte> findByFechaGeneracionBetween(LocalDate inicio, LocalDate fin);

    List<Reporte> findByUsuarioId(Integer usuarioId);
}
