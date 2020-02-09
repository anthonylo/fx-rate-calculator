package com.anthonylo.fx.calculator.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Map;

public class ExchangeRate {
  private String base;
  private String date;

  @JsonProperty("time_last_updated")
  private long timeLastUpdate;

  private Map<String, BigDecimal> rates;

  public String getBase() {
    return base;
  }

  public void setBase(final String base) {
    this.base = base;
  }

  public String getDate() {
    return date;
  }

  public void setDate(final String date) {
    this.date = date;
  }

  public long getTimeLastUpdate() {
    return timeLastUpdate;
  }

  public void setTimeLastUpdate(final long timeLastUpdate) {
    this.timeLastUpdate = timeLastUpdate;
  }

  public Map<String, BigDecimal> getRates() {
    return rates;
  }

  public void setRates(final Map<String, BigDecimal> rates) {
    this.rates = rates;
  }

  @Override
  public String toString() {
    return "ExchangeRate{"
        + "base='"
        + base
        + '\''
        + ", date='"
        + date
        + '\''
        + ", timeLastUpdate="
        + timeLastUpdate
        + ", rates="
        + rates
        + '}';
  }
}
