package dev.sobue.workshop.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;

/**
 * Main Class.
 *
 * @author Sho SOBUE
 */
@SpringBootApplication
public class ClientApplication {

  /**
   * Main Method.
   *
   * @param args command line arguments
   */
  public static void main(String[] args) {
    SpringApplication.run(ClientApplication.class, args);
  }

  @Bean
  RestClient restClient() {
    return RestClient.builder().build();
  }
}
