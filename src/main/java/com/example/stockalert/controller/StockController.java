package com.example.stockalert.controller;

import com.example.stockalert.dto.StockResponse;
import com.example.stockalert.dto.StockTarget;
import com.example.stockalert.mapper.StockMapper;
import com.example.stockalert.model.Stock;
import com.example.stockalert.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

import java.util.*;
@RestController
@RequestMapping("/stocks")
@RequiredArgsConstructor
public class StockController {

    private final StockService service;
    private final StockMapper mapper;

    @GetMapping
    public List<StockResponse> lista() {

        List<Stock> list = service.list();

        return list.stream()
                .map(mapper::toResponse)
                .toList();
    }

    @PostMapping
    public ResponseEntity<String> add(@RequestBody StockTarget stock) {

        BigDecimal price = StockService.parsePrice(stock.price());

        service.save(stock.symbol().toUpperCase(), price);

        return ResponseEntity.ok("Registro salvo com sucesso");
    }

    @PutMapping("/{symbol}")
    public ResponseEntity<String> updateStock(
            @PathVariable String symbol,
            @RequestBody StockTarget stock
    ) {
        service.getBySymbol(symbol);

        BigDecimal price = StockService.parsePrice(stock.price());

        service.update(symbol.toUpperCase(), price);

        return ResponseEntity.ok(
                "Preço alvo atualizado com sucesso para "
                        + symbol.toUpperCase() + " → " + price
        );
    }

    @DeleteMapping("/{symbol}")
    public ResponseEntity<String> delete(@PathVariable String symbol) {

        service.delete(symbol.toUpperCase());

        return ResponseEntity.ok(
                "Ativo removido com sucesso: " + symbol.toUpperCase()
        );
    }
}