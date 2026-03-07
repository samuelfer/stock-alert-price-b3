package com.example.stockalert.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailService {

  private final JavaMailSender mailSender;
  private static final Logger log =
          LoggerFactory.getLogger(EmailService.class);

  public void sendAlert(String to, List<String> stocks) {

    SimpleMailMessage message = new SimpleMailMessage();

    message.setFrom("marhastockb3Alert@gmail.com");
    message.setTo(to);

    message.setSubject("📈 Ações que atingiram o preço alvo");

    StringBuilder body = new StringBuilder();

    body.append("As seguintes ações atingiram o preço desejado:\n\n");

    for (String stock : stocks) {
      body.append("- ").append(stock).append("\n");
    }

    body.append("\nMonitor automático da bolsa.");

    message.setText(body.toString());

    try {
      mailSender.send(message);
      log.info("Email enviado com sucesso");
    } catch (Exception ex) {
      log.error("Erro ao enviar email", ex);
    }
  }
}