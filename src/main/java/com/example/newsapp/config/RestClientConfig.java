package com.example.newsapp.config;

import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration for HTTP Client beans.
 */
@Configuration
public class RestClientConfig {

  @Value("${news.api.connect-timeout-ms:5000}")
  private int connectTimeout;

  @Value("${news.api.read-timeout-ms:5000}")
  private int readTimeout;

  /**
   * Configures a RestClient bean with HTTP timeouts.
   *
   * @param builder RestTemplateBuilder provided by Spring
   * @return RestClient configured instance
   */
  @Bean
  public RestClient restClient(RestTemplateBuilder builder) {
    RestTemplate restTemplate = builder
        .setConnectTimeout(Duration.ofMillis(connectTimeout))
        .setReadTimeout(Duration.ofMillis(readTimeout))
        .build();

    return RestClient.builder(restTemplate)
        .defaultHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) SpringBoot/3.2 NewsApp/1.0")
        .defaultHeader("Accept", "application/json")
        .build();
  }
}
