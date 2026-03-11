package com.example.stockalert.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "stocks")
@Data
public class Stock {

    @Id
    private String symbol;

    private BigDecimal targetPrice;

    public Stock() {}

    public Stock(String symbol, BigDecimal targetPrice) {
        this.symbol = symbol;
        this.targetPrice = targetPrice;
    }
}