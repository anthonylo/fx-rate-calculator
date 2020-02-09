package com.anthonylo.fx.calculator.service;

import com.anthonylo.fx.calculator.beans.ExchangeRate;
import com.anthonylo.fx.calculator.beans.FXRate;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
class ForexService implements IForexService {
  private static final Logger logger = LoggerFactory.getLogger(ForexService.class);

  @Value("${forex.rate.api-url}")
  private String baseUrl;

  private final RestTemplate restTemplate;
  private final Gson gson;

  private final Map<String, FXRate> ratesCache;

  @Autowired
  public ForexService(final RestTemplate restTemplate, final Gson gson) {
    this.restTemplate = restTemplate;
    this.gson = gson;

    ratesCache = new ConcurrentHashMap<>();
  }

  @Override
  public List<String> fetchCurrencies() {
    return null;
  }

  @Override
  public FXRate fetchFXRate(final String currency) {
    if (ratesCache.containsKey(currency)) return ratesCache.get(currency);

    final ExchangeRate exchangeRate =
        restTemplate.getForObject(baseUrl + "/" + currency, ExchangeRate.class);

    final LocalDate localDate =
        LocalDate.from(DateTimeFormatter.ofPattern("yyyy-MM-dd").parse(exchangeRate.getDate()));

    return new FXRate(
        exchangeRate.getBase(),
        localDate,
        exchangeRate.getTimeLastUpdate(),
        exchangeRate.getRates());
  }

  @Override
  public BigDecimal convert(
      final String fromCurrency, final String toCurrency, final BigDecimal amount) {
    final FXRate fxRate = fetchFXRate(fromCurrency);
    final BigDecimal fxRateConversion = fxRate.getFxRate(toCurrency);
    return amount.multiply(fxRateConversion);
  }
}