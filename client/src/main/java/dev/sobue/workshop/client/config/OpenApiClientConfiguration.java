package dev.sobue.workshop.client.config;

import dev.sobue.workshop.client.invoker.ApiClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(OpenApiClientProperties.class)
public class OpenApiClientConfiguration {

  @Bean
  public ApiClient apiClient(OpenApiClientProperties properties) {
    ApiClient client = new ApiClient();
    client.setBasePath(properties.getBaseUrl());
    return client;
  }
}
