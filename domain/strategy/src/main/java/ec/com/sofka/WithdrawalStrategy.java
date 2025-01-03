package ec.com.sofka;

import java.math.BigDecimal;
import org.springframework.stereotype.Component;

@Component
public class WithdrawalStrategy implements ITransactionCostStrategy {

  @Override
  public BigDecimal calculateCost(BigDecimal amount) {
    return BigDecimal.valueOf(1.00); // Fixed fee for ATM withdrawal
  }

  @Override
  public TRANSACTION_TYPE getType() {
    return TRANSACTION_TYPE.ATM_WITHDRAWAL;
  }
}
