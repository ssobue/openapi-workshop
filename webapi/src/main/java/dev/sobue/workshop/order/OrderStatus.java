package dev.sobue.workshop.order;

import java.util.Locale;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {
  PENDING_PAYMENT("P"),
  PROCESSING("R"),
  SHIPPED("S"),
  CANCELLED("C"),
  FAILED("F");

  private final String code;

  public static OrderStatus fromCode(String code) {
    if (code == null) {
      throw new IllegalArgumentException("Order status must not be null");
    }
    for (OrderStatus status : values()) {
      if (status.code.equalsIgnoreCase(code)) {
        return status;
      }
    }
    return OrderStatus.valueOf(code.toUpperCase(Locale.ROOT));
  }
}
