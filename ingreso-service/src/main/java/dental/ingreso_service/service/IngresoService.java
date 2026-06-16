package dental.ingreso_service.service;

import dental.ingreso_service.client.InsumoClient;
import dental.ingreso_service.client.ProveedorClient;
import dental.ingreso_service.client.StockClient;
import dental.ingreso_service.dto.IngresoDetalleDTO;
import dental.ingreso_service.dto.InsumoDTO;
import dental.ingreso_service.dto.ProveedorDTO;
import dental.ingreso_service.exception.RecursoNoEncontradoException;
import dental.ingreso_service.model.Ingreso;
import dental.ingreso_service.repository.IngresoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IngresoService {

    private final IngresoRepository ingresoRepository;
    private final InsumoClient insumoClient;
    private final ProveedorClient proveedorClient;
    private final StockClient stockClient;

    public List<Ingreso> listarIngresos() {
        return ingresoRepository.findAll();
    }

    public Ingreso buscarPorId(Integer id) {
        return ingresoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Ingreso no encontrado con id: " + id));
    }

    public List<Ingreso> buscarPorInsumoId(Integer insumoId) {
        List<Ingreso> ingresos = ingresoRepository.findByInsumoId(insumoId);
        if (ingresos.isEmpty()) {
            throw new RecursoNoEncontradoException("No existen ingresos para el insumo id: " + insumoId);
        }
        return ingresos;
    }

    public List<Ingreso> buscarPorProveedorId(Integer proveedorId) {
        List<Ingreso> ingresos = ingresoRepository.findByProveedorId(proveedorId);
        if (ingresos.isEmpty()) {
            throw new RecursoNoEncontradoException("No existen ingresos para el proveedor id: " + proveedorId);
        }
        return ingresos;
    }

    public Ingreso guardarIngreso(Ingreso ingreso) {
        try {
            ingreso.setFecha(LocalDate.now());
            ingreso.setHora(LocalTime.now());
            Ingreso guardado = ingresoRepository.save(ingreso);
            // Actualiza el stock sumando la cantidad ingresada
            stockClient.actualizarCantidad(ingreso.getInsumoId(), ingreso.getCantidad());
            return guardado;
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar el ingreso: " + e.getMessage());
        }
    }

    public Ingreso actualizarIngreso(Integer id, Ingreso ingresoActualizado) {
        try {
            Ingreso ingreso = buscarPorId(id);
            ingreso.setCantidad(ingresoActualizado.getCantidad());
            ingreso.setNumeroFactura(ingresoActualizado.getNumeroFactura());
            ingreso.setObservacion(ingresoActualizado.getObservacion());
            ingreso.setInsumoId(ingresoActualizado.getInsumoId());
            ingreso.setProveedorId(ingresoActualizado.getProveedorId());
            return ingresoRepository.save(ingreso);
        } catch (RecursoNoEncontradoException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el ingreso: " + e.getMessage());
        }
    }

    public void eliminarIngreso(Integer id) {
        try {
            Ingreso ingreso = buscarPorId(id);
            ingresoRepository.delete(ingreso);
        } catch (RecursoNoEncontradoException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar el ingreso: " + e.getMessage());
        }
    }

    public IngresoDetalleDTO buscarDetallePorId(Integer id) {
        try {
            Ingreso ingreso = buscarPorId(id);
            InsumoDTO insumo = insumoClient.obtenerInsumoPorId(ingreso.getInsumoId());
            ProveedorDTO proveedor = proveedorClient.obtenerProveedorPorId(ingreso.getProveedorId());
            return new IngresoDetalleDTO(
                    ingreso.getIdIngreso(),
                    ingreso.getFecha(),
                    ingreso.getHora(),
                    ingreso.getCantidad(),
                    ingreso.getNumeroFactura(),
                    ingreso.getObservacion(),
                    insumo,
                    proveedor
            );
        } catch (RecursoNoEncontradoException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener el detalle del ingreso: " + e.getMessage());
        }
    }
}