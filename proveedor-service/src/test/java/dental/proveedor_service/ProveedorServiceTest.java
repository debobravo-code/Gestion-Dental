package dental.proveedor_service;

import dental.proveedor_service.exception.RecursoNoEncontradoException;
import dental.proveedor_service.model.Proveedor;
import dental.proveedor_service.repository.ProveedorRepository;
import dental.proveedor_service.service.ProveedorService;
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
class ProveedorServiceTest {

    @Mock
    private ProveedorRepository proveedorRepository;

    @InjectMocks
    private ProveedorService proveedorService;

    @Test
    void deberiaListarProveedores() {
        Proveedor proveedor = new Proveedor(
                1,
                "Dental Chile",
                "76.123.456-7",
                "987654321",
                "contacto@dentalchile.cl",
                "Santiago"
        );

        when(proveedorRepository.findAll()).thenReturn(List.of(proveedor));

        List<Proveedor> resultado = proveedorService.listarProveedores();

        assertEquals(1, resultado.size());
        assertEquals("Dental Chile", resultado.get(0).getNombre());

        verify(proveedorRepository).findAll();
    }

    @Test
    void deberiaBuscarProveedorPorId() {
        Proveedor proveedor = new Proveedor(
                1,
                "Dental Chile",
                "76.123.456-7",
                "987654321",
                "contacto@dentalchile.cl",
                "Santiago"
        );

        when(proveedorRepository.findById(1))
                .thenReturn(Optional.of(proveedor));

        Proveedor resultado = proveedorService.buscarPorId(1);

        assertEquals(1, resultado.getIdProveedor());
        assertEquals("Dental Chile", resultado.getNombre());

        verify(proveedorRepository).findById(1);
    }

    @Test
    void deberiaBuscarProveedorPorRut() {
        Proveedor proveedor = new Proveedor(
                1,
                "Dental Chile",
                "76.123.456-7",
                "987654321",
                "contacto@dentalchile.cl",
                "Santiago"
        );

        when(proveedorRepository.findByRut("76.123.456-7"))
                .thenReturn(Optional.of(proveedor));

        Proveedor resultado =
                proveedorService.buscarPorRut("76.123.456-7");

        assertEquals("76.123.456-7", resultado.getRut());

        verify(proveedorRepository).findByRut("76.123.456-7");
    }

    @Test
    void deberiaLanzarExcepcionCuandoProveedorNoExiste() {
        when(proveedorRepository.findById(99))
                .thenReturn(Optional.empty());

        assertThrows(
                RecursoNoEncontradoException.class,
                () -> proveedorService.buscarPorId(99)
        );

        verify(proveedorRepository).findById(99);
    }

    @Test
    void deberiaGuardarProveedor() {
        Proveedor proveedor = new Proveedor(
                null,
                "Proveedor Nuevo",
                "77.111.222-3",
                "912345678",
                "nuevo@correo.cl",
                "Valparaiso"
        );

        Proveedor guardado = new Proveedor(
                1,
                "Proveedor Nuevo",
                "77.111.222-3",
                "912345678",
                "nuevo@correo.cl",
                "Valparaiso"
        );

        when(proveedorRepository.save(proveedor))
                .thenReturn(guardado);

        Proveedor resultado =
                proveedorService.guardarProveedor(proveedor);

        assertEquals(1, resultado.getIdProveedor());

        verify(proveedorRepository).save(proveedor);
    }

    @Test
    void deberiaActualizarProveedor() {
        Proveedor existente = new Proveedor(
                1,
                "Proveedor Antiguo",
                "11.111.111-1",
                "999999999",
                "viejo@correo.cl",
                "Santiago"
        );

        Proveedor actualizado = new Proveedor(
                1,
                "Proveedor Nuevo",
                "22.222.222-2",
                "888888888",
                "nuevo@correo.cl",
                "Valparaiso"
        );

        when(proveedorRepository.findById(1))
                .thenReturn(Optional.of(existente));

        when(proveedorRepository.save(existente))
                .thenReturn(actualizado);

        Proveedor resultado =
                proveedorService.actualizarProveedor(1, actualizado);

        assertEquals("Proveedor Nuevo", resultado.getNombre());

        verify(proveedorRepository).findById(1);
        verify(proveedorRepository).save(existente);
    }

    @Test
    void deberiaEliminarProveedor() {
        Proveedor proveedor = new Proveedor(
                1,
                "Dental Chile",
                "76.123.456-7",
                "987654321",
                "contacto@dentalchile.cl",
                "Santiago"
        );

        when(proveedorRepository.findById(1))
                .thenReturn(Optional.of(proveedor));

        proveedorService.eliminarProveedor(1);

        verify(proveedorRepository).delete(proveedor);
    }
}