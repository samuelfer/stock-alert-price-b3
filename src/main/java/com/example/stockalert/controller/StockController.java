package com.example.stockalert.controller;

import com.example.stockalert.dto.StockTarget;
import com.example.stockalert.service.StockStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.*;

@RestController
@RequestMapping("/stocks")
@RequiredArgsConstructor
public class StockController {

    private final StockStorageService storage;

    @GetMapping
    public List<Map<String, String>> list() {
        Map<String, BigDecimal> targets = storage.getTargets();

        NumberFormat currencyFormat =
                NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        List<Map<String, String>> response = new ArrayList<>();

        for (Map.Entry<String, BigDecimal> entry : targets.entrySet()) {

            Map<String, String> item = new HashMap<>();
            item.put("ativo", entry.getKey());
            item.put("preco", currencyFormat.format(entry.getValue()));

            response.add(item);
        }

        return response;
    }

    @PostMapping
    public ResponseEntity<String> add(@RequestBody StockTarget stock) {

        if (!stock.symbol().matches("[A-Z0-9]{4,6}")) {
            return ResponseEntity.badRequest()
                    .body("Símbolo inválido");
        }

        Map<String, BigDecimal> targets = storage.getTargets();

        String normalized =
                stock.price().replace(",", ".");

        BigDecimal price = new BigDecimal(normalized);

        targets.put(stock.symbol().toUpperCase(), price);

        storage.saveTargets(targets);
        return ResponseEntity.ok(
                "Registro salvo com sucesso");
    }

    @PutMapping("/{symbol}")
    public ResponseEntity<String> updateStock(
            @PathVariable String symbol,
            @RequestBody StockTarget stock
    ) {
        String normalized =
                stock.price().replace(",", ".");

        BigDecimal price = new BigDecimal(normalized);
        storage.updateTarget(symbol, price);
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