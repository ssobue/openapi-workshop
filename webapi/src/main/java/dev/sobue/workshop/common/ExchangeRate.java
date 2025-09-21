package dev.sobue.workshop.common;

import java.math.BigDecimal;

public class ExchangeRate {

  private Long id;
  private Currency currency;
  private BigDecimal rate;

  public ExchangeRate() {
  }

  public ExchangeRate(Long id, Currency currency, BigDecimal rate) {
    this.id = id;
    this.currency = currency;
    this.rate = rate;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Currency getCurrency() {
    return currency;
  }

  public void setCurrency(Currency currency) {
    this.currency = currency;
  }

  public BigDecimal getRate() {
    return rate;
  }

  public void setRate(BigDecimal rate) {
    this.rate = rate;
  }
}
