package com.example.stockalert.mapper;

import com.example.stockalert.dto.StockResponse;
import com.example.stockalert.model.Stock;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

@Mapper(componentModel = "spring")
public interface StockMapper {

    @Mapping(target = "targetPrice", expression = "java(formatPrice(stock.getTargetPrice()))")
    StockResponse toResponse(Stock stock);

    default String formatPrice(BigDecimal price) {

        NumberFormat currencyFormat =
                NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        return currencyFormat.format(price);
    }
}