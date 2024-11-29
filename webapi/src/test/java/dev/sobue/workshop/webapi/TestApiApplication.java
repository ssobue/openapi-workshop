package dev.sobue.workshop.webapi;

import org.springframework.boot.SpringApplication;

public class TestApiApplication {

  public static void main(String[] args) {
    SpringApplication
        .from(ApiApplication::main)
        .run(args);
  }
}
