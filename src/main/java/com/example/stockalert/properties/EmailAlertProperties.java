package com.example.stockalert.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "alerts")
@Data
public class EmailAlertProperties {

    private String email;

}
