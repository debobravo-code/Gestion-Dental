package dental.destino_service.service;

import dental.destino_service.exception.RecursoNoEncontradoException;
import dental.destino_service.model.Destino;
import dental.destino_service.repository.DestinoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DestinoService {

    private final DestinoRepository destinoRepository;

    public List<Destino> listarDestinos() {
        return destinoRepository.findAll();
    }

    public Destino buscarPorId(Integer id) {
        return destinoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Destino no encontrado con id: " + id));
    }

    public List<Destino> buscarPorTipo(String tipo) {
        List<Destino> destinos = destinoRepository.findByTipo(tipo);
        if (destinos.isEmpty()) {
            throw new RecursoNoEncontradoException("No existen destinos del tipo: " + tipo);
        }
        return destinos;
    }

    public List<Destino> listarActivos() {
        return destinoRepository.findByActivo(true);
    }

    public Destino guardarDestino(Destino destino) {
        try {
            return destinoRepository.save(destino);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar el destino: " + e.getMessage());
        }
    }

    public Destino actualizarDestino(Integer id, Destino destinoActualizado) {
        try {
            Destino destino = buscarPorId(id);
            destino.setNombre(destinoActualizado.getNombre());
            destino.setTipo(destinoActualizado.getTipo());
            destino.setDescripcion(destinoActualizado.getDescripcion());
            destino.setActivo(destinoActualizado.getActivo());
            return destinoRepository.save(destino);
        } catch (RecursoNoEncontradoException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el destino: " + e.getMessage());
        }
    }

    public void eliminarDestino(Integer id) {
        try {
            Destino destino = buscarPorId(id);
            destinoRepository.delete(destino);
        } catch (RecursoNoEncontradoException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar el destino: " + e.getMessage());
        }
    }
}
