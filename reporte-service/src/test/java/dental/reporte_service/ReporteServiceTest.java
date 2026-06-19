package dental.reporte_service;

import dental.reporte_service.exception.RecursoNoEncontradoException;
import dental.reporte_service.model.Reporte;
import dental.reporte_service.repository.ReporteRepository;
import dental.reporte_service.service.ReporteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReporteServiceTest {

    @Mock
    private ReporteRepository reporteRepository;

    @InjectMocks
    private ReporteService reporteService;

    @Test
    void deberiaListarReportes() {
        Reporte reporte = new Reporte(
                1,
                "STOCK",
                LocalDate.now().minusDays(7),
                LocalDate.now(),
                LocalDate.now(),
                "Resumen de stock",
                1
        );

        when(reporteRepository.findAll()).thenReturn(List.of(reporte));

        List<Reporte> resultado = reporteService.listarReportes();

        assertEquals(1, resultado.size());
        assertEquals("STOCK", resultado.get(0).getTipoReporte());
        verify(reporteRepository).findAll();
    }

    @Test
    void deberiaBuscarReportePorId() {
        Reporte reporte = new Reporte(
                1,
                "INGRESOS",
                LocalDate.now().minusDays(10),
                LocalDate.now(),
                LocalDate.now(),
                "Reporte de ingresos",
                1
        );

        when(reporteRepository.findById(1)).thenReturn(Optional.of(reporte));

        Reporte resultado = reporteService.buscarPorId(1);

        assertEquals(1, resultado.getIdReporte());
        assertEquals("INGRESOS", resultado.getTipoReporte());
        verify(reporteRepository).findById(1);
    }

    @Test
    void deberiaLanzarExcepcionCuandoReporteNoExiste() {
        when(reporteRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class, () -> {
            reporteService.buscarPorId(99);
        });

        verify(reporteRepository).findById(99);
    }

    @Test
    void deberiaBuscarReportesPorTipo() {
        Reporte reporte = new Reporte(
                1,
                "EGRESOS",
                LocalDate.now().minusDays(5),
                LocalDate.now(),
                LocalDate.now(),
                "Reporte de egresos",
                2
        );

        when(reporteRepository.findByTipoReporte("EGRESOS"))
                .thenReturn(List.of(reporte));

        List<Reporte> resultado = reporteService.buscarPorTipo("EGRESOS");

        assertEquals(1, resultado.size());
        assertEquals("EGRESOS", resultado.get(0).getTipoReporte());
        verify(reporteRepository).findByTipoReporte("EGRESOS");
    }

    @Test
    void deberiaBuscarReportesPorRangoFecha() {
        LocalDate inicio = LocalDate.now().minusDays(30);
        LocalDate fin = LocalDate.now();

        Reporte reporte = new Reporte(
                1,
                "STOCK",
                inicio,
                fin,
                LocalDate.now(),
                "Reporte mensual",
                1
        );

        when(reporteRepository.findByFechaGeneracionBetween(inicio, fin))
                .thenReturn(List.of(reporte));

        List<Reporte> resultado = reporteService.buscarPorRangoFecha(inicio, fin);

        assertEquals(1, resultado.size());
        verify(reporteRepository).findByFechaGeneracionBetween(inicio, fin);
    }

    @Test
    void deberiaBuscarReportesPorUsuarioId() {
        Reporte reporte = new Reporte(
                1,
                "STOCK",
                LocalDate.now().minusDays(7),
                LocalDate.now(),
                LocalDate.now(),
                "Reporte generado por usuario",
                3
        );

        when(reporteRepository.findByUsuarioId(3)).thenReturn(List.of(reporte));

        List<Reporte> resultado = reporteService.buscarPorUsuarioId(3);

        assertEquals(1, resultado.size());
        assertEquals(3, resultado.get(0).getUsuarioId());
        verify(reporteRepository).findByUsuarioId(3);
    }

    @Test
    void deberiaGenerarReporteConFechaActual() {
        Reporte reporte = new Reporte(
                null,
                "STOCK",
                LocalDate.now().minusDays(7),
                LocalDate.now(),
                null,
                "Reporte de stock",
                1
        );

        Reporte guardado = new Reporte(
                1,
                "STOCK",
                LocalDate.now().minusDays(7),
                LocalDate.now(),
                LocalDate.now(),
                "Reporte de stock",
                1
        );

        when(reporteRepository.save(reporte)).thenReturn(guardado);

        Reporte resultado = reporteService.generarReporte(reporte);

        assertEquals(1, resultado.getIdReporte());
        assertNotNull(reporte.getFechaGeneracion());
        verify(reporteRepository).save(reporte);
    }

    @Test
    void deberiaEliminarReporte() {
        Reporte reporte = new Reporte(
                1,
                "STOCK",
                LocalDate.now().minusDays(7),
                LocalDate.now(),
                LocalDate.now(),
                "Reporte de stock",
                1
        );

        when(reporteRepository.findById(1)).thenReturn(Optional.of(reporte));

        reporteService.eliminarReporte(1);

        verify(reporteRepository).findById(1);
        verify(reporteRepository).delete(reporte);
    }
}