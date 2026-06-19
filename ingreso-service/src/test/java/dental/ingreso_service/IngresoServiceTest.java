package dental.ingreso_service;

import dental.ingreso_service.client.InsumoClient;
import dental.ingreso_service.client.ProveedorClient;
import dental.ingreso_service.client.StockClient;
import dental.ingreso_service.exception.RecursoNoEncontradoException;
import dental.ingreso_service.model.Ingreso;
import dental.ingreso_service.repository.IngresoRepository;
import dental.ingreso_service.service.IngresoService;
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
class IngresoServiceTest {

    @Mock
    private IngresoRepository ingresoRepository;

    @Mock
    private InsumoClient insumoClient;

    @Mock
    private ProveedorClient proveedorClient;

    @Mock
    private StockClient stockClient;

    @InjectMocks
    private IngresoService ingresoService;

    @Test
    void deberiaListarIngresos() {
        Ingreso ingreso = new Ingreso();
        ingreso.setIdIngreso(1);
        ingreso.setCantidad(5);
        ingreso.setNumeroFactura("FAC-001");
        ingreso.setInsumoId(1);
        ingreso.setProveedorId(1);

        when(ingresoRepository.findAll()).thenReturn(List.of(ingreso));

        List<Ingreso> resultado = ingresoService.listarIngresos();

        assertEquals(1, resultado.size());
        assertEquals(5, resultado.get(0).getCantidad());
        verify(ingresoRepository).findAll();
    }

    @Test
    void deberiaBuscarIngresoPorId() {
        Ingreso ingreso = new Ingreso();
        ingreso.setIdIngreso(1);
        ingreso.setCantidad(3);
        ingreso.setNumeroFactura("FAC-002");
        ingreso.setInsumoId(1);
        ingreso.setProveedorId(1);

        when(ingresoRepository.findById(1)).thenReturn(Optional.of(ingreso));

        Ingreso resultado = ingresoService.buscarPorId(1);

        assertEquals(1, resultado.getIdIngreso());
        assertEquals(3, resultado.getCantidad());
        verify(ingresoRepository).findById(1);
    }

    @Test
    void deberiaLanzarExcepcionCuandoIngresoNoExiste() {
        when(ingresoRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class, () -> {
            ingresoService.buscarPorId(99);
        });

        verify(ingresoRepository).findById(99);
    }

    @Test
    void deberiaGuardarIngresoYActualizarStock() {
        Ingreso ingreso = new Ingreso();
        ingreso.setCantidad(2);
        ingreso.setNumeroFactura("FAC-003");
        ingreso.setObservacion("Prueba unitaria");
        ingreso.setInsumoId(1);
        ingreso.setProveedorId(1);

        Ingreso ingresoGuardado = new Ingreso();
        ingresoGuardado.setIdIngreso(1);
        ingresoGuardado.setCantidad(2);
        ingresoGuardado.setNumeroFactura("FAC-003");
        ingresoGuardado.setObservacion("Prueba unitaria");
        ingresoGuardado.setInsumoId(1);
        ingresoGuardado.setProveedorId(1);

        when(ingresoRepository.save(any(Ingreso.class))).thenReturn(ingresoGuardado);

        Ingreso resultado = ingresoService.guardarIngreso(ingreso);

        assertEquals(1, resultado.getIdIngreso());
        assertEquals(2, resultado.getCantidad());
        assertNotNull(ingreso.getFecha());
        assertNotNull(ingreso.getHora());

        verify(ingresoRepository).save(ingreso);
        verify(stockClient).actualizarCantidad(1, 2);
    }
}