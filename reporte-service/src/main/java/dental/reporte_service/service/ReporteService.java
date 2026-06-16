package dental.reporte_service.service;

import dental.reporte_service.exception.RecursoNoEncontradoException;
import dental.reporte_service.model.Reporte;
import dental.reporte_service.repository.ReporteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReporteService {

    private final ReporteRepository reporteRepository;

    public List<Reporte> listarReportes() {
        return reporteRepository.findAll();
    }

    public Reporte buscarPorId(Integer id) {
        return reporteRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Reporte no encontrado con id: " + id));
    }

    public List<Reporte> buscarPorTipo(String tipo) {
        return reporteRepository.findByTipoReporte(tipo);
    }

    public List<Reporte> buscarPorRangoFecha(LocalDate inicio, LocalDate fin) {
        return reporteRepository.findByFechaGeneracionBetween(inicio, fin);
    }

    public List<Reporte> buscarPorUsuarioId(Integer usuarioId) {
        List<Reporte> reportes = reporteRepository.findByUsuarioId(usuarioId);
        if (reportes.isEmpty()) {
            throw new RecursoNoEncontradoException("No existen reportes para el usuario id: " + usuarioId);
        }
        return reportes;
    }

    public Reporte generarReporte(Reporte reporte) {
        try {
            reporte.setFechaGeneracion(LocalDate.now());
            return reporteRepository.save(reporte);
        } catch (Exception e) {
            throw new RuntimeException("Error al generar el reporte: " + e.getMessage());
        }
    }

    public void eliminarReporte(Integer id) {
        try {
            Reporte reporte = buscarPorId(id);
            reporteRepository.delete(reporte);
        } catch (RecursoNoEncontradoException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar el reporte: " + e.getMessage());
        }
    }
}
