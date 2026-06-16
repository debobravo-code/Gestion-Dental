package dental.insumo_service.service;

import dental.insumo_service.exception.RecursoNoEncontradoException;
import dental.insumo_service.model.Insumo;
import dental.insumo_service.repository.InsumoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InsumoService {

    private final InsumoRepository insumoRepository;

    public List<Insumo> listarInsumos() {
        return insumoRepository.findAll();
    }

    public Insumo buscarPorId(Integer id) {
        return insumoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Insumo no encontrado con id: " + id));
    }

    public List<Insumo> buscarPorCategoria(String categoria) {
        List<Insumo> insumos = insumoRepository.findByCategoria(categoria);
        if (insumos.isEmpty()) {
            throw new RecursoNoEncontradoException("No existen insumos para la categoría: " + categoria);
        }
        return insumos;
    }

    public List<Insumo> buscarProximosAVencer() {
        LocalDate limite = LocalDate.now().plusDays(30);
        return insumoRepository.findByFechaVencimientoBefore(limite);
    }

    public Insumo guardarInsumo(Insumo insumo) {
        try {
            return insumoRepository.save(insumo);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar el insumo: " + e.getMessage());
        }
    }

    public Insumo actualizarInsumo(Integer id, Insumo insumoActualizado) {
        try {
            Insumo insumo = buscarPorId(id);
            insumo.setNombre(insumoActualizado.getNombre());
            insumo.setCategoria(insumoActualizado.getCategoria());
            insumo.setUnidadMedida(insumoActualizado.getUnidadMedida());
            insumo.setFechaVencimiento(insumoActualizado.getFechaVencimiento());
            insumo.setStockMinimo(insumoActualizado.getStockMinimo());
            return insumoRepository.save(insumo);
        } catch (RecursoNoEncontradoException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el insumo: " + e.getMessage());
        }
    }

    public void eliminarInsumo(Integer id) {
        try {
            Insumo insumo = buscarPorId(id);
            insumoRepository.delete(insumo);
        } catch (RecursoNoEncontradoException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar el insumo: " + e.getMessage());
        }
    }
}
