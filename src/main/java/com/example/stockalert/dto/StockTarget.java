package com.example.stockalert.dto;

import java.math.BigDecimal;

public record StockTarget(
        String symbol,
        BigDecimal price
) {}