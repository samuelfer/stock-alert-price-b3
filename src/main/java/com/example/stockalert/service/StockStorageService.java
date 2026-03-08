package com.example.stockalert.service;

import com.example.stockalert.scheduler.StockScheduler;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Service
public class StockStorageService {

    private static final Logger log =
            LoggerFactory.getLogger(StockStorageService.class);

    private final ObjectMapper mapper = new ObjectMapper();

    private final Path file =
            Paths.get(System.getProperty("user.dir"), "data", "stocks.json");

    public Map<String, BigDecimal> getTargets() {

        try {

            Map<String, Map<String, BigDecimal>> data =
                    mapper.readValue(
                            file.toFile(),
                            new TypeReference<Map<String, Map<String, BigDecimal>>>() {}
                    );

            return data.getOrDefault("targets", new HashMap<>());

        } catch (Exception e) {
            log.error("Erro ao ler stocks.json", e);
            throw new RuntimeException("Erro ao ler stocks.json", e);

        }
    }
    public void saveTargets(Map<String, BigDecimal> targets) {

        try {

            Map<String, Object> data = new HashMap<>();
            data.put("targets", targets);

            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(file.toFile(), data);

        } catch (Exception e) {
            log.error("Erro ao salvar arquivo", e);
            throw new RuntimeException("Erro ao salvar arquivo", e);
        }
    }

    public void updateTarget(String symbol, BigDecimal price) {

        try {

            Map<String, Map<String, BigDecimal>> data =
                    mapper.readValue(
                            file.toFile(),
                            new TypeReference<Map<String, Map<String, BigDecimal>>>() {}
                    );

            Map<String, BigDecimal> targets =
                    data.getOrDefault("targets", new HashMap<>());

            targets.put(symbol, price);

            data.put("targets", targets);

            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(file.toFile(), data);

        } catch (Exception e) {
            log.error("Erro ao atualizar stocks.json", e);
            throw new RuntimeException("Erro ao atualizar stocks.json", e);

        }
    }
}