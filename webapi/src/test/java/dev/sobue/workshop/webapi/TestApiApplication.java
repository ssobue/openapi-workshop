package dev.sobue.workshop.webapi;

import org.springframework.boot.SpringApplication;

/**
 * Main Class for Test.
 *
 * @author Sho SOBUE
 * @see ApiApplication
 */
public class TestApiApplication {

  /**
   * Main method.
   *
   * @param args command line arguments
   */
  public static void main(String[] args) {
    SpringApplication
        // Use the main method of ApiApplication.
        .from(ApiApplication::main)
        // Launch Application with MySQL using Testcontainers.
        .with(ContainerConfiguration.class)
        .run(args);
  }
}
