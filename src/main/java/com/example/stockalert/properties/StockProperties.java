package com.example.stockalert.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "stocks")
@Data
public class StockProperties {

    private Map<String, BigDecimal> targets;

}