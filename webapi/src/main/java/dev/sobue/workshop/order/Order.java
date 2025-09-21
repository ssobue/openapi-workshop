package dev.sobue.workshop.order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Order {

  private Long id;
  private String customerName;
  private OrderStatus status;
  private LocalDateTime orderDate;
  private LocalDateTime updatedOn;
  @Getter(AccessLevel.NONE)
  @Setter(AccessLevel.NONE)
  private List<OrderItem> items = new ArrayList<>();

  public Order(Long id, String customerName, OrderStatus status, LocalDateTime orderDate,
      LocalDateTime updatedOn) {
    this.id = id;
    this.customerName = customerName;
    this.status = status;
    this.orderDate = orderDate;
    this.updatedOn = updatedOn;
  }

  public List<OrderItem> getItems() {
    return Collections.unmodifiableList(new ArrayList<>(items));
  }

  public void setItems(List<OrderItem> items) {
    if (items == null) {
      this.items = new ArrayList<>();
    } else {
      this.items = new ArrayList<>(items);
    }
  }
}
