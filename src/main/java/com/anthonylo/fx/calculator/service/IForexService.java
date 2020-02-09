package com.anthonylo.fx.calculator.service;

import com.anthonylo.fx.calculator.beans.FXRate;

import java.math.BigDecimal;
import java.util.List;

public interface IForexService {
  List<String> fetchCurrencies();

  FXRate fetchFXRate(final String currency);

  BigDecimal convert(final String fromCurrency, final String toCurrency, final BigDecimal amount);
}
