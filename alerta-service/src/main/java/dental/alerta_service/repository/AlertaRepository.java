package dental.alerta_service.repository;

import dental.alerta_service.model.Alerta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertaRepository extends JpaRepository<Alerta, Integer> {

    List<Alerta> findByRevisada(Boolean revisada);

    List<Alerta> findByInsumoId(Integer insumoId);

    List<Alerta> findByTipoAlerta(String tipoAlerta);
}
