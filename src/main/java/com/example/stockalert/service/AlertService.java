package com.example.stockalert.service;

import com.example.stockalert.model.StockAlert;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Service
public class AlertService {

  private final Map<String, List<StockAlert>> alerts = new ConcurrentHashMap<>();

  public void addAlert(StockAlert alert){
    alerts.computeIfAbsent(alert.getSymbol(), k -> new ArrayList<>()).add(alert);
  }

}