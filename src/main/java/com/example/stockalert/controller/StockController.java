package com.example.stockalert.controller;

import com.example.stockalert.dto.StockTarget;
import com.example.stockalert.service.StockStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/stocks")
@RequiredArgsConstructor
public class StockController {

    private final StockStorageService storage;

    @GetMapping
    public Map<String, BigDecimal> list() {
        return storage.getTargets();
    }

    @PostMapping
    public ResponseEntity<String> add(@RequestBody StockTarget stock) {

        Map<String, BigDecimal> targets =
                storage.getTargets();

        targets.put(stock.symbol(), stock.price());

        storage.saveTargets(targets);
        return ResponseEntity.ok(
                "Registro salvo com sucesso");
    }

    @PutMapping("/{symbol}")
    public ResponseEntity<String> updateStock(
            @PathVariable String symbol,
            @RequestBody StockTarget stock
    ) {
        storage.updateTarget(symbol, stock.price());
        return ResponseEntity.ok(
                "Preço alvo atualizado com sucesso para " +
                        stock.symbol() + " → " + stock.price()
        );
    }

    @DeleteMapping("/{symbol}")
    public void delete(@PathVariable String symbol) {

        Map<String, BigDecimal> targets =
                storage.getTargets();

        targets.remove(symbol);

        storage.saveTargets(targets);
    }
}