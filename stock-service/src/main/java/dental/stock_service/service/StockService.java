package dental.stock_service.service;

import dental.stock_service.exception.RecursoNoEncontradoException;
import dental.stock_service.model.Stock;
import dental.stock_service.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;

    public List<Stock> listarStock() {
        return stockRepository.findAll();
    }

    public Stock buscarPorId(Integer id) {
        return stockRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Stock no encontrado con id: " + id));
    }

    public Stock buscarPorInsumoId(Integer insumoId) {
        return stockRepository.findByInsumoId(insumoId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Stock no encontrado para insumo id: " + insumoId));
    }

    public List<Stock> listarStockBajo() {
        return stockRepository.findAll().stream()
                .filter(s -> s.getCantidadActual() <= s.getCantidadMinima())
                .toList();
    }

    public Stock guardarStock(Stock stock) {
        try {
            return stockRepository.save(stock);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar el stock: " + e.getMessage());
        }
    }

    public Stock actualizarCantidad(Integer insumoId, Integer cantidad) {
        try {
            Stock stock = buscarPorInsumoId(insumoId);
            stock.setCantidadActual(stock.getCantidadActual() + cantidad);
            return stockRepository.save(stock);
        } catch (RecursoNoEncontradoException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar stock: " + e.getMessage());
        }
    }

    public void eliminarStock(Integer id) {
        try {
            Stock stock = buscarPorId(id);
            stockRepository.delete(stock);
        } catch (RecursoNoEncontradoException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar el stock: " + e.getMessage());
        }
    }
}
