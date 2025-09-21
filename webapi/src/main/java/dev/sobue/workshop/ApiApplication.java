package dev.sobue.workshop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Class.
 *
 * @author Sho SOBUE
 */
@SpringBootApplication
@MapperScan({
    "dev.sobue.workshop.common.mybatis.mapper",
    "dev.sobue.workshop.product.mybatis.mapper",
    "dev.sobue.workshop.order.mybatis.mapper"})
public class ApiApplication {

  /**
   * Main Method.
   *
   * @param args command line arguments
   */
  public static void main(String[] args) {
    SpringApplication.run(ApiApplication.class, args);
  }
}
