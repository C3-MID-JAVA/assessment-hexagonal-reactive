package ec.com.sofka;

import java.math.BigDecimal;

@FunctionalInterface
public interface ITransactionCostStrategy {
  BigDecimal calculateCost(BigDecimal amount);

  default TRANSACTION_TYPE getType() {
    return null;
  }
}