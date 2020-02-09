package com.anthonylo.fx.calculator.service;

import com.anthonylo.fx.calculator.beans.ExchangeRate;
import com.anthonylo.fx.calculator.beans.FXRate;
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

  private final Map<String, FXRate> ratesCache;

  @Autowired
  public ForexService(final RestTemplate restTemplate) {
    this.restTemplate = restTemplate;

    ratesCache = new ConcurrentHashMap<>();
  }

  @Override
  public List<String> fetchCurrencies() {
    return null;
  }

  @Override
  public FXRate fetchFXRate(final String currency) {
    logger.info("Fetching FX Rate for {}", currency);
    if (ratesCache.containsKey(currency)) return ratesCache.get(currency);

    final ExchangeRate exchangeRate =
        restTemplate.getForObject(baseUrl + "/" + currency, ExchangeRate.class);

    assert exchangeRate != null;

    final LocalDate localDate =
        LocalDate.from(DateTimeFormatter.ofPattern("yyyy-MM-dd").parse(exchangeRate.getDate()));

    ratesCache.put(
        currency,
        new FXRate(
            exchangeRate.getBase(),
            localDate,
            exchangeRate.getTimeLastUpdate(),
            exchangeRate.getRates()));
    return ratesCache.get(currency);
  }

  @Override
  public BigDecimal convert(
      final String fromCurrency, final String toCurrency, final BigDecimal amount) {
    final FXRate fxRate = fetchFXRate(fromCurrency);
    final BigDecimal fxRateConversion = fxRate.getFxRate(toCurrency);
    final BigDecimal convertedAmount = amount.multiply(fxRateConversion);
    logger.info(
        "Calculated FX conversion rate of {} from {} to {} is {}",
        amount,
        fromCurrency,
        toCurrency,
        convertedAmount);
    return convertedAmount;
  }
}
