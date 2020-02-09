package com.anthonylo.fx.calculator.beans;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

public class FXRate {
  private final String currency;
  private final LocalDate date;
  private final long lastUpdated;
  private final Map<String, BigDecimal> ratesToCurrencies;

  public FXRate(
      final String currency,
      final LocalDate date,
      final long lastUpdated,
      final Map<String, BigDecimal> ratesToCurrencies) {
    this.currency = currency;
    this.date = date;
    this.lastUpdated = lastUpdated;
    this.ratesToCurrencies = ratesToCurrencies;
  }

  public String getCurrency() {
    return currency;
  }

  public LocalDate getDate() {
    return date;
  }

  public long getLastUpdated() {
    return lastUpdated;
  }

  public Map<String, BigDecimal> getRatesToCurrencies() {
    return ratesToCurrencies;
  }

  public BigDecimal getFxRate(final String currency) {
    if (ratesToCurrencies.containsKey(currency)) return ratesToCurrencies.get(currency);
    return BigDecimal.ZERO;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    final FXRate fxRate = (FXRate) o;
    return lastUpdated == fxRate.lastUpdated
        && Objects.equals(currency, fxRate.currency)
        && Objects.equals(date, fxRate.date)
        && Objects.equals(ratesToCurrencies, fxRate.ratesToCurrencies);
  }

  @Override
  public int hashCode() {
    return Objects.hash(currency, date, lastUpdated, ratesToCurrencies);
  }
}
