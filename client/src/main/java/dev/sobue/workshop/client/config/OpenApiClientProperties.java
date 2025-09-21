package dev.sobue.workshop.client.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "workshop.api")
public class OpenApiClientProperties {

  /**
   * Base URL that the generated API client should target.
   */
  private String baseUrl = "http://localhost:8080/api/v1";

  public String getBaseUrl() {
    return baseUrl;
  }

  public void setBaseUrl(String baseUrl) {
    this.baseUrl = baseUrl;
  }
}
