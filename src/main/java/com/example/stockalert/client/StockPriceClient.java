package com.example.stockalert.client;

import com.example.stockalert.dto.BrapiResponse;
import com.example.stockalert.dto.BrapiStock;
import com.example.stockalert.properties.BrapiProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.*;

@Component
@RequiredArgsConstructor
public class StockPriceClient {

  private final WebClient webClient;
  private final BrapiProperties brapiProperties;

  public Mono<Map<String, BigDecimal>> getPrices(List<String> symbols) {

    return Flux.fromIterable(symbols)

            .flatMap(symbol ->

                    webClient
                            .get()
                            .uri("https://brapi.dev/api/quote/" + symbol)

                            .headers(headers ->
                                    headers.setBearerAuth(brapiProperties.getToken().trim())
                            )

                            .retrieve()
                            .bodyToMono(BrapiResponse.class)

                            .map(response -> {

                              BrapiStock stock = response.getResults().get(0);

                              return Map.entry(
                                      stock.getSymbol(),
                                      stock.getRegularMarketPrice()
                              );

                            })

            )

            .collectMap(Map.Entry::getKey, Map.Entry::getValue);
  }
}