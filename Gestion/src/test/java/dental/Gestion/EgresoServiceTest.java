package dental.Gestion;

import dental.Gestion.client.DestinoClient;
import dental.Gestion.client.InsumoClient;
import dental.Gestion.client.StockClient;
import dental.Gestion.exception.RecursoNoEncontradoException;
import dental.Gestion.model.Egreso;
import dental.Gestion.repository.EgresoRepository;
import dental.Gestion.service.EgresoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EgresoServiceTest {

    @Mock
    private EgresoRepository egresoRepository;

    @Mock
    private InsumoClient insumoClient;

    @Mock
    private DestinoClient destinoClient;

    @Mock
    private StockClient stockClient;

    @InjectMocks
    private EgresoService egresoService;

    @Test
    void testListarEgresos() {
        Egreso egreso = crearEgreso();

        when(egresoRepository.findAll()).thenReturn(List.of(egreso));

        List<Egreso> resultado = egresoService.listarEgresos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Atención odontológica", resultado.get(0).getMotivo());

        verify(egresoRepository, times(1)).findAll();
    }

    @Test
    void testBuscarPorIdExiste() {
        Egreso egreso = crearEgreso();

        when(egresoRepository.findById(1)).thenReturn(Optional.of(egreso));

        Egreso resultado = egresoService.buscarPorId(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getIdEgreso());
        assertEquals("Atención odontológica", resultado.getMotivo());

        verify(egresoRepository, times(1)).findById(1);
    }

    @Test
    void testBuscarPorIdNoExiste() {
        when(egresoRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class, () ->
                egresoService.buscarPorId(99)
        );

        verify(egresoRepository, times(1)).findById(99);
    }

    @Test
    void testBuscarPorInsumoId() {
        Egreso egreso = crearEgreso();

        when(egresoRepository.findByInsumoId(1)).thenReturn(List.of(egreso));

        List<Egreso> resultado = egresoService.buscarPorInsumoId(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(1, resultado.get(0).getInsumoId());

        verify(egresoRepository, times(1)).findByInsumoId(1);
    }

    @Test
    void testBuscarPorInsumoIdSinResultados() {
        when(egresoRepository.findByInsumoId(99)).thenReturn(List.of());

        assertThrows(RecursoNoEncontradoException.class, () ->
                egresoService.buscarPorInsumoId(99)
        );

        verify(egresoRepository, times(1)).findByInsumoId(99);
    }

    @Test
    void testBuscarPorDestinoId() {
        Egreso egreso = crearEgreso();

        when(egresoRepository.findByDestinoId(1)).thenReturn(List.of(egreso));

        List<Egreso> resultado = egresoService.buscarPorDestinoId(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(1, resultado.get(0).getDestinoId());

        verify(egresoRepository, times(1)).findByDestinoId(1);
    }

    @Test
    void testBuscarPorFecha() {
        LocalDate fecha = LocalDate.of(2026, 6, 12);
        Egreso egreso = crearEgreso();
        egreso.setFecha(fecha);

        when(egresoRepository.findByFecha(fecha)).thenReturn(List.of(egreso));

        List<Egreso> resultado = egresoService.buscarPorFecha(fecha);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(fecha, resultado.get(0).getFecha());

        verify(egresoRepository, times(1)).findByFecha(fecha);
    }

    @Test
    void testBuscarPorRangoFechas() {
        LocalDate inicio = LocalDate.of(2026, 6, 1);
        LocalDate fin = LocalDate.of(2026, 6, 30);

        Egreso egreso = crearEgreso();

        when(egresoRepository.findByFechaBetween(inicio, fin)).thenReturn(List.of(egreso));

        List<Egreso> resultado = egresoService.buscarPorRangoFechas(inicio, fin);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());

        verify(egresoRepository, times(1)).findByFechaBetween(inicio, fin);
    }

    private Egreso crearEgreso() {
        Egreso egreso = new Egreso();
        egreso.setIdEgreso(1);
        egreso.setFecha(LocalDate.of(2026, 6, 12));
        egreso.setHora(LocalTime.of(10, 30));
        egreso.setCantidad(5);
        egreso.setMotivo("Atención odontológica");
        egreso.setObservacion("Entrega de insumos para box dental");
        egreso.setInsumoId(1);
        egreso.setDestinoId(1);
        return egreso;
    }
}