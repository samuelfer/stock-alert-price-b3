package com.example.stockalert.model;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CreateAlertRequest {

  private String symbol;
  private BigDecimal targetPrice;
  private String email;

}