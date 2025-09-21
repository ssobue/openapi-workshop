package dev.sobue.workshop.order;

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
public class OrderItem {

  private Long id;
  private Long orderId;
  private Long productId;
  private Integer quantity;
  private BigDecimal price;
  private Currency currency;
}
