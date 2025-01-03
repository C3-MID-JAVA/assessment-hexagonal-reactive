package ec.com.sofka;

import java.math.BigDecimal;
import org.springframework.stereotype.Component;

@Component
public class ATMDepositStrategy implements ITransactionCostStrategy {

  @Override
  public BigDecimal calculateCost(BigDecimal amount) {
    return BigDecimal.valueOf(2.00);
  }

  @Override
  public TRANSACTION_TYPE getType() {
    return TRANSACTION_TYPE.ATM_DEPOSIT;
  }
}
