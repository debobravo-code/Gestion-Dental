package dental.destino_service;

import dental.destino_service.exception.RecursoNoEncontradoException;
import dental.destino_service.model.Destino;
import dental.destino_service.repository.DestinoRepository;
import dental.destino_service.service.DestinoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DestinoServiceTest {

    @Mock
    private DestinoRepository destinoRepository;

    @InjectMocks
    private DestinoService destinoService;

    @Test
    void deberiaListarDestinos() {
        Destino destino = new Destino(1, "Box Dental", "CLINICO", "Destino clínico", true);

        when(destinoRepository.findAll()).thenReturn(List.of(destino));

        List<Destino> resultado = destinoService.listarDestinos();

        assertEquals(1, resultado.size());
        assertEquals("Box Dental", resultado.get(0).getNombre());
        verify(destinoRepository).findAll();
    }

    @Test
    void deberiaBuscarDestinoPorId() {
        Destino destino = new Destino(1, "Bodega", "INTERNO", "Bodega central", true);

        when(destinoRepository.findById(1)).thenReturn(Optional.of(destino));

        Destino resultado = destinoService.buscarPorId(1);

        assertEquals(1, resultado.getIdDestino());
        assertEquals("Bodega", resultado.getNombre());
        verify(destinoRepository).findById(1);
    }

    @Test
    void deberiaLanzarExcepcionCuandoDestinoNoExiste() {
        when(destinoRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class, () -> {
            destinoService.buscarPorId(99);
        });

        verify(destinoRepository).findById(99);
    }

    @Test
    void deberiaBuscarDestinosPorTipo() {
        Destino destino = new Destino(1, "Box 1", "CLINICO", "Box atención", true);

        when(destinoRepository.findByTipo("CLINICO")).thenReturn(List.of(destino));

        List<Destino> resultado = destinoService.buscarPorTipo("CLINICO");

        assertEquals(1, resultado.size());
        assertEquals("CLINICO", resultado.get(0).getTipo());
        verify(destinoRepository).findByTipo("CLINICO");
    }

    @Test
    void deberiaListarDestinosActivos() {
        Destino destino = new Destino(1, "Box Activo", "CLINICO", "Activo", true);

        when(destinoRepository.findByActivo(true)).thenReturn(List.of(destino));

        List<Destino> resultado = destinoService.listarActivos();

        assertEquals(1, resultado.size());
        assertTrue(resultado.get(0).getActivo());
        verify(destinoRepository).findByActivo(true);
    }

    @Test
    void deberiaGuardarDestino() {
        Destino destino = new Destino(null, "Nuevo Destino", "INTERNO", "Descripción", true);
        Destino guardado = new Destino(1, "Nuevo Destino", "INTERNO", "Descripción", true);

        when(destinoRepository.save(destino)).thenReturn(guardado);

        Destino resultado = destinoService.guardarDestino(destino);

        assertEquals(1, resultado.getIdDestino());
        assertEquals("Nuevo Destino", resultado.getNombre());
        verify(destinoRepository).save(destino);
    }

    @Test
    void deberiaActualizarDestino() {
        Destino existente = new Destino(1, "Destino Antiguo", "INTERNO", "Antes", true);
        Destino actualizado = new Destino(1, "Destino Nuevo", "CLINICO", "Después", false);

        when(destinoRepository.findById(1)).thenReturn(Optional.of(existente));
        when(destinoRepository.save(existente)).thenReturn(actualizado);

        Destino resultado = destinoService.actualizarDestino(1, actualizado);

        assertEquals("Destino Nuevo", resultado.getNombre());
        assertEquals("CLINICO", resultado.getTipo());
        assertFalse(resultado.getActivo());

        verify(destinoRepository).findById(1);
        verify(destinoRepository).save(existente);
    }

    @Test
    void deberiaEliminarDestino() {
        Destino destino = new Destino(1, "Destino", "INTERNO", "Descripción", true);

        when(destinoRepository.findById(1)).thenReturn(Optional.of(destino));

        destinoService.eliminarDestino(1);

        verify(destinoRepository).findById(1);
        verify(destinoRepository).delete(destino);
    }
}