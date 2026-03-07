package com.example.stockalert.scheduler;

import com.example.stockalert.client.StockPriceClient;
import com.example.stockalert.properties.EmailAlertProperties;
import com.example.stockalert.properties.StockProperties;
import com.example.stockalert.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

@Component
@RequiredArgsConstructor
public class StockScheduler {

  private final StockPriceClient priceClient;
  private final EmailService emailService;
  private final StockProperties stockProperties;
  private final EmailAlertProperties emailAlertProperties;
  private static final Logger log =
            LoggerFactory.getLogger(EmailService.class);

  /**
   *De 15 em 15 minutos consulta a api que verifica os valores na B3
   */
  @Scheduled(fixedDelay = 900000)
  public void checkStocks() {

    Map<String, BigDecimal> targets = stockProperties.getTargets();

    if (targets == null || targets.isEmpty()) return;

    List<String> symbols = new ArrayList<>(targets.keySet());
    DecimalFormat df = new DecimalFormat("#,##0.00");

    priceClient.getPrices(symbols)
            .doOnError(error ->
                    log.error("Erro no monitor de ações", error)
            )

            .onErrorResume(e -> Mono.empty())
            .subscribe(prices -> {

              List<String> triggered = new ArrayList<>();

              for (String symbol : symbols) {

                BigDecimal currentPrice = prices.get(symbol);
                BigDecimal target = targets.get(symbol);

                if (currentPrice.compareTo(target) <= 0) {

                  triggered.add(
                          symbol + " → R$ " + df.format(currentPrice)
                  );
                }

              }

                // Envia email somente se alguma acao ou FII tiver atingido o preço alvo
                //Tambem evita enviar o mesmo email varias vezes no dia
              if (!triggered.isEmpty()) {
                  log.info("Ações que atingiram o preço alvo: {}", triggered);
                  emailService.sendAlert(
                        emailAlertProperties.getEmail(),
                        triggered
                );

              }

            });

  }
}