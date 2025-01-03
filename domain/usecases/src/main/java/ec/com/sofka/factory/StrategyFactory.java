package ec.com.sofka.factory;

import ec.com.sofka.ITransactionCostStrategy;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

@Component
public class StrategyFactory {
  private final Map<String, ITransactionCostStrategy> strategies;

  public StrategyFactory(Map<String, ITransactionCostStrategy> strategies) {
    this.strategies = strategies;
  }

  public ITransactionCostStrategy getStrategy(String transactionType) {
    return strategies.getOrDefault(transactionType, amount -> BigDecimal.ZERO);
  }
}
