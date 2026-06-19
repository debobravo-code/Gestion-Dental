package dental.alerta_service;

import dental.alerta_service.exception.RecursoNoEncontradoException;
import dental.alerta_service.model.Alerta;
import dental.alerta_service.repository.AlertaRepository;
import dental.alerta_service.service.AlertaService;
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
class AlertaServiceTest {

    @Mock
    private AlertaRepository alertaRepository;

    @InjectMocks
    private AlertaService alertaService;

    @Test
    void deberiaListarAlertas() {
        Alerta alerta = new Alerta(
                1,
                1,
                "STOCK_BAJO",
                "Stock bajo para guantes",
                LocalDate.now(),
                false
        );

        when(alertaRepository.findAll()).thenReturn(List.of(alerta));

        List<Alerta> resultado = alertaService.listarAlertas();

        assertEquals(1, resultado.size());
        assertEquals("STOCK_BAJO", resultado.get(0).getTipoAlerta());
        verify(alertaRepository).findAll();
    }

    @Test
    void deberiaBuscarAlertaPorId() {
        Alerta alerta = new Alerta(
                1,
                1,
                "VENCIMIENTO",
                "Insumo próximo a vencer",
                LocalDate.now(),
                false
        );

        when(alertaRepository.findById(1)).thenReturn(Optional.of(alerta));

        Alerta resultado = alertaService.buscarPorId(1);

        assertEquals(1, resultado.getIdAlerta());
        assertEquals("VENCIMIENTO", resultado.getTipoAlerta());
        verify(alertaRepository).findById(1);
    }

    @Test
    void deberiaLanzarExcepcionCuandoAlertaNoExiste() {
        when(alertaRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class, () -> {
            alertaService.buscarPorId(99);
        });

        verify(alertaRepository).findById(99);
    }

    @Test
    void deberiaListarAlertasNoRevisadas() {
        Alerta alerta = new Alerta(
                1,
                1,
                "STOCK_BAJO",
                "Stock bajo para mascarillas",
                LocalDate.now(),
                false
        );

        when(alertaRepository.findByRevisada(false)).thenReturn(List.of(alerta));

        List<Alerta> resultado = alertaService.listarNoRevisadas();

        assertEquals(1, resultado.size());
        assertFalse(resultado.get(0).getRevisada());
        verify(alertaRepository).findByRevisada(false);
    }

    @Test
    void deberiaBuscarAlertasPorInsumoId() {
        Alerta alerta = new Alerta(
                1,
                5,
                "STOCK_BAJO",
                "Stock bajo para pasta dental",
                LocalDate.now(),
                false
        );

        when(alertaRepository.findByInsumoId(5)).thenReturn(List.of(alerta));

        List<Alerta> resultado = alertaService.buscarPorInsumoId(5);

        assertEquals(1, resultado.size());
        assertEquals(5, resultado.get(0).getInsumoId());
        verify(alertaRepository).findByInsumoId(5);
    }

    @Test
    void deberiaBuscarAlertasPorTipo() {
        Alerta alerta = new Alerta(
                1,
                1,
                "VENCIMIENTO",
                "Insumo próximo a vencer",
                LocalDate.now(),
                false
        );

        when(alertaRepository.findByTipoAlerta("VENCIMIENTO")).thenReturn(List.of(alerta));

        List<Alerta> resultado = alertaService.buscarPorTipo("VENCIMIENTO");

        assertEquals(1, resultado.size());
        assertEquals("VENCIMIENTO", resultado.get(0).getTipoAlerta());
        verify(alertaRepository).findByTipoAlerta("VENCIMIENTO");
    }

    @Test
    void deberiaGuardarAlertaConFechaActualYNoRevisada() {
        Alerta alerta = new Alerta(
                null,
                1,
                "STOCK_BAJO",
                "Stock bajo para guantes",
                null,
                true
        );

        Alerta guardada = new Alerta(
                1,
                1,
                "STOCK_BAJO",
                "Stock bajo para guantes",
                LocalDate.now(),
                false
        );

        when(alertaRepository.save(alerta)).thenReturn(guardada);

        Alerta resultado = alertaService.guardarAlerta(alerta);

        assertEquals(1, resultado.getIdAlerta());
        assertNotNull(alerta.getFechaGeneracion());
        assertFalse(alerta.getRevisada());
        verify(alertaRepository).save(alerta);
    }

    @Test
    void deberiaMarcarAlertaComoRevisada() {
        Alerta alerta = new Alerta(
                1,
                1,
                "STOCK_BAJO",
                "Stock bajo",
                LocalDate.now(),
                false
        );

        Alerta revisada = new Alerta(
                1,
                1,
                "STOCK_BAJO",
                "Stock bajo",
                LocalDate.now(),
                true
        );

        when(alertaRepository.findById(1)).thenReturn(Optional.of(alerta));
        when(alertaRepository.save(alerta)).thenReturn(revisada);

        Alerta resultado = alertaService.marcarComoRevisada(1);

        assertTrue(resultado.getRevisada());
        verify(alertaRepository).findById(1);
        verify(alertaRepository).save(alerta);
    }

    @Test
    void deberiaEliminarAlerta() {
        Alerta alerta = new Alerta(
                1,
                1,
                "STOCK_BAJO",
                "Stock bajo",
                LocalDate.now(),
                false
        );

        when(alertaRepository.findById(1)).thenReturn(Optional.of(alerta));

        alertaService.eliminarAlerta(1);

        verify(alertaRepository).findById(1);
        verify(alertaRepository).delete(alerta);
    }
}