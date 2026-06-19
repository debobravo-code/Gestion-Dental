package dental.insumo_service;

import dental.insumo_service.exception.RecursoNoEncontradoException;
import dental.insumo_service.model.Insumo;
import dental.insumo_service.repository.InsumoRepository;
import dental.insumo_service.service.InsumoService;
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
class InsumoServiceTest {

    @Mock
    private InsumoRepository insumoRepository;

    @InjectMocks
    private InsumoService insumoService;

    @Test
    void deberiaListarInsumos() {
        Insumo insumo = new Insumo(
                1,
                "Guantes de Látex",
                "Desechables",
                "Caja",
                LocalDate.now().plusMonths(6),
                10
        );

        when(insumoRepository.findAll()).thenReturn(List.of(insumo));

        List<Insumo> resultado = insumoService.listarInsumos();

        assertEquals(1, resultado.size());
        assertEquals("Guantes de Látex", resultado.get(0).getNombre());
        verify(insumoRepository).findAll();
    }

    @Test
    void deberiaBuscarInsumoPorId() {
        Insumo insumo = new Insumo(
                1,
                "Mascarillas",
                "Desechables",
                "Caja",
                LocalDate.now().plusMonths(3),
                20
        );

        when(insumoRepository.findById(1)).thenReturn(Optional.of(insumo));

        Insumo resultado = insumoService.buscarPorId(1);

        assertEquals(1, resultado.getIdInsumo());
        assertEquals("Mascarillas", resultado.getNombre());
        verify(insumoRepository).findById(1);
    }

    @Test
    void deberiaLanzarExcepcionCuandoInsumoNoExiste() {
        when(insumoRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class, () -> {
            insumoService.buscarPorId(99);
        });

        verify(insumoRepository).findById(99);
    }

    @Test
    void deberiaBuscarInsumosPorCategoria() {
        Insumo insumo = new Insumo(
                1,
                "Jeringa",
                "Instrumental",
                "Unidad",
                LocalDate.now().plusMonths(12),
                5
        );

        when(insumoRepository.findByCategoria("Instrumental")).thenReturn(List.of(insumo));

        List<Insumo> resultado = insumoService.buscarPorCategoria("Instrumental");

        assertEquals(1, resultado.size());
        assertEquals("Instrumental", resultado.get(0).getCategoria());
        verify(insumoRepository).findByCategoria("Instrumental");
    }

    @Test
    void deberiaGuardarInsumo() {
        Insumo insumo = new Insumo(
                null,
                "Pasta dental",
                "Higiene",
                "Unidad",
                LocalDate.now().plusMonths(10),
                15
        );

        Insumo guardado = new Insumo(
                1,
                "Pasta dental",
                "Higiene",
                "Unidad",
                LocalDate.now().plusMonths(10),
                15
        );

        when(insumoRepository.save(insumo)).thenReturn(guardado);

        Insumo resultado = insumoService.guardarInsumo(insumo);

        assertEquals(1, resultado.getIdInsumo());
        assertEquals("Pasta dental", resultado.getNombre());
        verify(insumoRepository).save(insumo);
    }

    @Test
    void deberiaActualizarInsumo() {
        Insumo existente = new Insumo(
                1,
                "Guantes",
                "Desechables",
                "Caja",
                LocalDate.now().plusMonths(6),
                10
        );

        Insumo actualizado = new Insumo(
                1,
                "Guantes Nitrilo",
                "Desechables",
                "Caja",
                LocalDate.now().plusMonths(8),
                20
        );

        when(insumoRepository.findById(1)).thenReturn(Optional.of(existente));
        when(insumoRepository.save(existente)).thenReturn(actualizado);

        Insumo resultado = insumoService.actualizarInsumo(1, actualizado);

        assertEquals("Guantes Nitrilo", resultado.getNombre());
        assertEquals(20, resultado.getStockMinimo());
        verify(insumoRepository).findById(1);
        verify(insumoRepository).save(existente);
    }

    @Test
    void deberiaEliminarInsumo() {
        Insumo insumo = new Insumo(
                1,
                "Alcohol",
                "Limpieza",
                "Botella",
                LocalDate.now().plusMonths(4),
                8
        );

        when(insumoRepository.findById(1)).thenReturn(Optional.of(insumo));

        insumoService.eliminarInsumo(1);

        verify(insumoRepository).findById(1);
        verify(insumoRepository).delete(insumo);
    }
}