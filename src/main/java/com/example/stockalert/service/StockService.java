package com.example.stockalert.service;

import com.example.stockalert.dto.StockResponse;
import com.example.stockalert.model.Stock;
import com.example.stockalert.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository repository;

    public StockResponse getBySymbol(String symbol) {

        Stock stock = repository.findBySymbol(symbol)
                .orElseThrow(() -> new RuntimeException("Ativo não encontrado"));

        return new StockResponse(stock.getSymbol(), stock.getTargetPrice().toString());
    }

    public List<Stock> list() {
        return repository.findAll();
    }

    public void save(String symbol, BigDecimal price) {

        Stock stock = new Stock(symbol.toUpperCase(), price);

        repository.save(stock);
    }

    public void update(String symbol, BigDecimal price) {

        Stock stock = repository.findById(symbol)
                .orElseThrow(() -> new RuntimeException("Ativo não encontrado"));

        stock.setTargetPrice(price);

        repository.save(stock);
    }

    public void delete(String symbol) {
        repository.deleteById(symbol);
    }

    public static BigDecimal parsePrice(String valor) {

        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("Preço inválido");
        }

        String normalized = valor
                .replace("R$", "")
                .replace(",", ".")
                .replace("\u00A0", "")
                .replaceAll("\\s+", "")
                .trim();

        return new BigDecimal(normalized);
    }

}