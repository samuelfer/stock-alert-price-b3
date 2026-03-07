package com.example.stockalert.service;

import com.example.stockalert.model.StockAlert;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class AlertServiceTest {

  AlertService service = new AlertService();

  @Test
  void shouldAddAlert(){

    StockAlert alert = new StockAlert();
    alert.setSymbol("PETR4");
    alert.setTargetPrice(BigDecimal.valueOf(30));
    alert.setEmail("test@test.com");

    service.addAlert(alert);

    assertTrue(service.getAlerts().containsKey("PETR4"));

  }

}