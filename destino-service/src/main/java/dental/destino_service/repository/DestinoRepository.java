package dental.destino_service.repository;

import dental.destino_service.model.Destino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DestinoRepository extends JpaRepository<Destino, Integer> {

    List<Destino> findByTipo(String tipo);

    List<Destino> findByActivo(Boolean activo);
}
