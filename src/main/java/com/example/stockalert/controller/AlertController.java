package com.example.stockalert.controller;

import com.example.stockalert.model.CreateAlertRequest;
import com.example.stockalert.model.StockAlert;
import com.example.stockalert.service.AlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alerts")
@RequiredArgsConstructor
public class AlertController {

  private final AlertService alertService;

  @PostMapping
  public ResponseEntity<?> createAlert(@RequestBody CreateAlertRequest request) {

    try {

      StockAlert alert = new StockAlert();

      alert.setSymbol(request.getSymbol());
      alert.setTargetPrice(request.getTargetPrice());
      alert.setEmail(request.getEmail());

      alertService.addAlert(alert);

      return ResponseEntity.ok("Alerta registrado");

    } catch (Exception ex) {

      return ResponseEntity
              .status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body(ex.getMessage());
    }
  }

}