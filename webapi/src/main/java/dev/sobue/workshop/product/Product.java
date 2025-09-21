package dev.sobue.workshop.product;

import dev.sobue.workshop.common.Currency;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Product {

  private Long id;
  private String name;
  private Currency currency;
  private BigDecimal price;
  private Integer stock;
}
