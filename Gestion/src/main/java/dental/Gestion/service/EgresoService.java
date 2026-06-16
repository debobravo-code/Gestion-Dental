package dental.Gestion.service;

import dental.Gestion.client.DestinoClient;
import dental.Gestion.client.InsumoClient;
import dental.Gestion.client.StockClient;
import dental.Gestion.dto.EgresoDetalleDTO;
import dental.Gestion.dto.InsumoDTO;
import dental.Gestion.exception.RecursoNoEncontradoException;
import dental.Gestion.model.Egreso;
import dental.Gestion.repository.EgresoRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EgresoService {

    private final EgresoRepository egresoRepository;
    private final InsumoClient insumoClient;
    private final DestinoClient destinoClient;
    private final StockClient stockClient;

    public List<Egreso> listarEgresos() {
        return egresoRepository.findAll();
    }

    public Egreso buscarPorId(Integer id) {
        return egresoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Egreso no encontrado con id: " + id));
    }

    public List<Egreso> buscarPorInsumoId(Integer insumoId) {
        List<Egreso> egresos = egresoRepository.findByInsumoId(insumoId);

        if (egresos.isEmpty()) {
            throw new RecursoNoEncontradoException("No existen egresos para el insumo id: " + insumoId);
        }

        return egresos;
    }

    public List<Egreso> buscarPorDestinoId(Integer destinoId) {
        List<Egreso> egresos = egresoRepository.findByDestinoId(destinoId);

        if (egresos.isEmpty()) {
            throw new RecursoNoEncontradoException("No existen egresos para el destino id: " + destinoId);
        }

        return egresos;
    }

    public List<Egreso> buscarPorFecha(LocalDate fecha) {
        List<Egreso> egresos = egresoRepository.findByFecha(fecha);

        if (egresos.isEmpty()) {
            throw new RecursoNoEncontradoException("No existen egresos para la fecha: " + fecha);
        }

        return egresos;
    }

    public List<Egreso> buscarPorRangoFechas(LocalDate inicio, LocalDate fin) {
        List<Egreso> egresos = egresoRepository.findByFechaBetween(inicio, fin);

        if (egresos.isEmpty()) {
            throw new RecursoNoEncontradoException(
                    "No existen egresos entre las fechas: " + inicio + " y " + fin);
        }

        return egresos;
    }

    public Egreso guardarEgreso(Egreso egreso) {

        try {
            insumoClient.obtenerInsumoPorId(egreso.getInsumoId());
        } catch (FeignException.NotFound e) {
            throw new RecursoNoEncontradoException(
                    "Insumo no encontrado con id: " + egreso.getInsumoId()
                            + ". Registre el insumo antes de crear un egreso.");
        } catch (FeignException e) {
            throw new RuntimeException(
                    "No se pudo contactar al servicio de insumos. Verifique que esté disponible.");
        }

        try {
            destinoClient.obtenerDestinoPorId(egreso.getDestinoId());
        } catch (FeignException.NotFound e) {
            throw new RecursoNoEncontradoException(
                    "Destino no encontrado con id: " + egreso.getDestinoId()
                            + ". Registre el destino antes de crear un egreso.");
        } catch (FeignException e) {
            throw new RuntimeException(
                    "No se pudo contactar al servicio de destinos. Verifique que esté disponible.");
        }

        try {
            egreso.setFecha(LocalDate.now());
            egreso.setHora(LocalTime.now());

            Egreso egresoGuardado = egresoRepository.save(egreso);

            try {
                stockClient.actualizarCantidad(egreso.getInsumoId(), -egreso.getCantidad());
            } catch (FeignException.NotFound e) {
                System.out.println("ADVERTENCIA: No existe stock registrado para el insumo id: "
                        + egreso.getInsumoId() + ". El egreso fue guardado pero el stock no se actualizó.");
            } catch (FeignException e) {
                System.out.println("ADVERTENCIA: No se pudo actualizar el stock. El egreso fue guardado.");
            }

            return egresoGuardado;

        } catch (RecursoNoEncontradoException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar el egreso: " + e.getMessage());
        }
    }

    public Egreso actualizarEgreso(Integer id, Egreso egresoActualizado) {

        Egreso egreso = buscarPorId(id);

        try {
            insumoClient.obtenerInsumoPorId(egresoActualizado.getInsumoId());
        } catch (FeignException.NotFound e) {
            throw new RecursoNoEncontradoException(
                    "Insumo no encontrado con id: " + egresoActualizado.getInsumoId()
                            + ". No se puede actualizar el egreso con un insumo inexistente.");
        } catch (FeignException e) {
            throw new RuntimeException(
                    "No se pudo contactar al servicio de insumos. Verifique que esté disponible.");
        }

        try {
            destinoClient.obtenerDestinoPorId(egresoActualizado.getDestinoId());
        } catch (FeignException.NotFound e) {
            throw new RecursoNoEncontradoException(
                    "Destino no encontrado con id: " + egresoActualizado.getDestinoId()
                            + ". No se puede actualizar el egreso con un destino inexistente.");
        } catch (FeignException e) {
            throw new RuntimeException(
                    "No se pudo contactar al servicio de destinos. Verifique que esté disponible.");
        }

        try {
            egreso.setCantidad(egresoActualizado.getCantidad());
            egreso.setMotivo(egresoActualizado.getMotivo());
            egreso.setObservacion(egresoActualizado.getObservacion());
            egreso.setInsumoId(egresoActualizado.getInsumoId());
            egreso.setDestinoId(egresoActualizado.getDestinoId());

            return egresoRepository.save(egreso);

        } catch (RecursoNoEncontradoException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el egreso: " + e.getMessage());
        }
    }

    public void eliminarEgreso(Integer id) {
        try {
            Egreso egreso = buscarPorId(id);
            egresoRepository.delete(egreso);
        } catch (RecursoNoEncontradoException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar el egreso: " + e.getMessage());
        }
    }

    public EgresoDetalleDTO buscarDetallePorId(Integer id) {

        Egreso egreso = buscarPorId(id);

        InsumoDTO insumo;
        try {
            insumo = insumoClient.obtenerInsumoPorId(egreso.getInsumoId());
        } catch (FeignException.NotFound e) {
            throw new RecursoNoEncontradoException(
                    "El insumo con id: " + egreso.getInsumoId()
                            + " ya no existe en el servicio de insumos.");
        } catch (FeignException e) {
            throw new RuntimeException(
                    "No se pudo contactar al servicio de insumos para obtener el detalle.");
        }

        return new EgresoDetalleDTO(
                egreso.getIdEgreso(),
                egreso.getFecha(),
                egreso.getHora(),
                egreso.getCantidad(),
                egreso.getMotivo(),
                egreso.getObservacion(),
                insumo,
                egreso.getDestinoId()
        );
    }
}