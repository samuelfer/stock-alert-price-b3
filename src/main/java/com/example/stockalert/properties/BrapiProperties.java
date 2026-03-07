package com.example.stockalert.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "brapi")
@Data
public class BrapiProperties {

    private String token;

}
