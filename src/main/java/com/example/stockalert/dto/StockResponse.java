package com.example.stockalert.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockResponse {

    private String symbol;
    private String targetPrice;
}