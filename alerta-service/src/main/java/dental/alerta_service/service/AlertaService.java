package dental.alerta_service.service;

import dental.alerta_service.exception.RecursoNoEncontradoException;
import dental.alerta_service.model.Alerta;
import dental.alerta_service.repository.AlertaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlertaService {

    private final AlertaRepository alertaRepository;

    public List<Alerta> listarAlertas() {
        return alertaRepository.findAll();
    }

    public Alerta buscarPorId(Integer id) {
        return alertaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Alerta no encontrada con id: " + id));
    }

    public List<Alerta> listarNoRevisadas() {
        return alertaRepository.findByRevisada(false);
    }

    public List<Alerta> buscarPorInsumoId(Integer insumoId) {
        List<Alerta> alertas = alertaRepository.findByInsumoId(insumoId);
        if (alertas.isEmpty()) {
            throw new RecursoNoEncontradoException("No existen alertas para el insumo id: " + insumoId);
        }
        return alertas;
    }

    public List<Alerta> buscarPorTipo(String tipo) {
        return alertaRepository.findByTipoAlerta(tipo);
    }

    public Alerta guardarAlerta(Alerta alerta) {
        try {
            alerta.setFechaGeneracion(LocalDate.now());
            alerta.setRevisada(false);
            return alertaRepository.save(alerta);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar la alerta: " + e.getMessage());
        }
    }

    public Alerta marcarComoRevisada(Integer id) {
        try {
            Alerta alerta = buscarPorId(id);
            alerta.setRevisada(true);
            return alertaRepository.save(alerta);
        } catch (RecursoNoEncontradoException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al marcar alerta como revisada: " + e.getMessage());
        }
    }

    public void eliminarAlerta(Integer id) {
        try {
            Alerta alerta = buscarPorId(id);
            alertaRepository.delete(alerta);
        } catch (RecursoNoEncontradoException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar la alerta: " + e.getMessage());
        }
    }
}
