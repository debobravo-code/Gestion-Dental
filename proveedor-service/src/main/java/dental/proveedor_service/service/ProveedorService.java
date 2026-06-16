package dental.proveedor_service.service;

import dental.proveedor_service.exception.RecursoNoEncontradoException;
import dental.proveedor_service.model.Proveedor;
import dental.proveedor_service.repository.ProveedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProveedorService {

    private final ProveedorRepository proveedorRepository;

    public List<Proveedor> listarProveedores() {
        return proveedorRepository.findAll();
    }

    public Proveedor buscarPorId(Integer id) {
        return proveedorRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Proveedor no encontrado con id: " + id));
    }

    public Proveedor buscarPorRut(String rut) {
        return proveedorRepository.findByRut(rut)
                .orElseThrow(() -> new RecursoNoEncontradoException("Proveedor no encontrado con RUT: " + rut));
    }

    public Proveedor guardarProveedor(Proveedor proveedor) {
        try {
            return proveedorRepository.save(proveedor);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar el proveedor: " + e.getMessage());
        }
    }

    public Proveedor actualizarProveedor(Integer id, Proveedor proveedorActualizado) {
        try {
            Proveedor proveedor = buscarPorId(id);
            proveedor.setNombre(proveedorActualizado.getNombre());
            proveedor.setRut(proveedorActualizado.getRut());
            proveedor.setTelefono(proveedorActualizado.getTelefono());
            proveedor.setEmail(proveedorActualizado.getEmail());
            proveedor.setDireccion(proveedorActualizado.getDireccion());
            return proveedorRepository.save(proveedor);
        } catch (RecursoNoEncontradoException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el proveedor: " + e.getMessage());
        }
    }

    public void eliminarProveedor(Integer id) {
        try {
            Proveedor proveedor = buscarPorId(id);
            proveedorRepository.delete(proveedor);
        } catch (RecursoNoEncontradoException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar el proveedor: " + e.getMessage());
        }
    }
}
