package com.example.stockalert.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BrapiStock {

    private String symbol;
    private BigDecimal regularMarketPrice;

}
