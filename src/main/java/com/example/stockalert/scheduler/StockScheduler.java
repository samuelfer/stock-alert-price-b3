package com.example.stockalert.scheduler;

import com.example.stockalert.client.StockPriceClient;
import com.example.stockalert.model.Stock;
import com.example.stockalert.properties.EmailAlertProperties;
import com.example.stockalert.service.EmailService;
import com.example.stockalert.service.StockService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class StockScheduler {

  private final StockPriceClient priceClient;
  private final EmailService emailService;
  private final EmailAlertProperties emailAlertProperties;
  private final StockService stockService;
  private static final Logger log =
            LoggerFactory.getLogger(StockScheduler.class);

  /**
   *De 15 em 15 minutos consulta a api que verifica os valores na B3
   */
  @Scheduled(fixedDelay = 60000)
  public void checkStocks() throws IOException {

      List<Stock> stocks = stockService.list();

      if (stocks.isEmpty()) {
          log.info("Nenhum ativo cadastrado para monitoramento");
          return;
      }

      Map<String, BigDecimal> targets = stocks.stream()
              .collect(Collectors.toMap(
                      Stock::getSymbol,
                      Stock::getTargetPrice
              ));

      List<String> symbols = new ArrayList<>(targets.keySet());

    NumberFormat currencyFormat =
            NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

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

                if (currentPrice == null) {
                  log.warn("Preço não encontrado para {}", symbol);
                  continue;
                }

                if (currentPrice.compareTo(target) <= 0) {

                  triggered.add(
                          symbol + " → R$ " + currencyFormat.format(currentPrice)
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