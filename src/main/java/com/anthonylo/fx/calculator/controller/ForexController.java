package com.anthonylo.fx.calculator.controller;

import com.anthonylo.fx.calculator.beans.FXRate;
import com.anthonylo.fx.calculator.service.IForexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping
public class ForexController {
  private final IForexService forexService;

  @Autowired
  public ForexController(final IForexService forexService) {
    this.forexService = forexService;
  }

  @GetMapping("/fetch/{currency}")
  public FXRate getRate(@PathVariable final String currency) {
    return forexService.fetchFXRate(currency);
  }

  @GetMapping(value = "/{fromCurrency}/to/{toCurrency}")
  public BigDecimal convert(
      @PathVariable final String fromCurrency,
      @PathVariable final String toCurrency,
      @RequestParam final BigDecimal amount) {
    return forexService.convert(fromCurrency, toCurrency, amount);
  }
}
