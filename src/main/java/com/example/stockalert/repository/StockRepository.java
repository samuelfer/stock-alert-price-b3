package com.example.stockalert.repository;

import com.example.stockalert.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, String> {
    Optional<Stock> findBySymbol(String symbol);
}
