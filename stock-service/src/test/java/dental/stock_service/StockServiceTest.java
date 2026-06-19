package dental.stock_service;

import dental.stock_service.exception.RecursoNoEncontradoException;
import dental.stock_service.model.Stock;
import dental.stock_service.repository.StockRepository;
import dental.stock_service.service.StockService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StockServiceTest {

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private StockService stockService;

    @Test
    void deberiaListarStock() {
        Stock stock = new Stock(1, 1, 10, 5);

        when(stockRepository.findAll()).thenReturn(List.of(stock));

        List<Stock> resultado = stockService.listarStock();

        assertEquals(1, resultado.size());
        assertEquals(10, resultado.get(0).getCantidadActual());
        verify(stockRepository).findAll();
    }

    @Test
    void deberiaBuscarStockPorId() {
        Stock stock = new Stock(1, 1, 10, 5);

        when(stockRepository.findById(1)).thenReturn(Optional.of(stock));

        Stock resultado = stockService.buscarPorId(1);

        assertEquals(1, resultado.getIdStock());
        assertEquals(1, resultado.getInsumoId());
        verify(stockRepository).findById(1);
    }

    @Test
    void deberiaLanzarExcepcionCuandoStockNoExiste() {
        when(stockRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class, () -> {
            stockService.buscarPorId(99);
        });

        verify(stockRepository).findById(99);
    }

    @Test
    void deberiaActualizarCantidadDeStock() {
        Stock stock = new Stock(1, 1, 10, 5);
        Stock stockActualizado = new Stock(1, 1, 15, 5);

        when(stockRepository.findByInsumoId(1)).thenReturn(Optional.of(stock));
        when(stockRepository.save(stock)).thenReturn(stockActualizado);

        Stock resultado = stockService.actualizarCantidad(1, 5);

        assertEquals(15, resultado.getCantidadActual());
        verify(stockRepository).findByInsumoId(1);
        verify(stockRepository).save(stock);
    }
}