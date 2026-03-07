package com.example.stockalert.config;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Component
public class StockConfig {

    public Map<String, BigDecimal> getTargets() {

        Map<String, BigDecimal> targets = new HashMap<>();

        targets.put("PETR4", new BigDecimal("42.16"));
        targets.put("BBSE", new BigDecimal("34.20"));
        targets.put("VALE3", new BigDecimal("60"));
        targets.put("ITUB4", new BigDecimal("28"));

        return targets;
    }

}