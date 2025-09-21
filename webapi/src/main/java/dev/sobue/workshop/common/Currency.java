package dev.sobue.workshop.common;

import java.util.Locale;

public enum Currency {
  USD,
  JPY,
  EUR;

  public static Currency fromCode(String code) {
    if (code == null) {
      throw new IllegalArgumentException("Currency code must not be null");
    }
    return Currency.valueOf(code.toUpperCase(Locale.ROOT));
  }

  public String getCode() {
    return name();
  }
}
