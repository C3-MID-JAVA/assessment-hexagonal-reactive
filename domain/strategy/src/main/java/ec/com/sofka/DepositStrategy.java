package ec.com.sofka;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DepositStrategy implements ITransactionCostStrategy {

  @Override
  public BigDecimal calculateCost(BigDecimal amount) {
    return BigDecimal.ZERO;
  }

  @Override
  public TRANSACTION_TYPE getType() {
    return TRANSACTION_TYPE.BRANCH_DEPOSIT;
  }
}
