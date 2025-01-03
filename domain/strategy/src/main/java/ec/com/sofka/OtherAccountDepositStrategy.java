package ec.com.sofka;

import java.math.BigDecimal;
import org.springframework.stereotype.Component;

@Component
public class OtherAccountDepositStrategy implements ITransactionCostStrategy {

  @Override
  public BigDecimal calculateCost(BigDecimal amount) {
    return BigDecimal.valueOf(1.50);
  }

  @Override
  public TRANSACTION_TYPE getType() {
    return TRANSACTION_TYPE.ONLINE_DEPOSIT;
  }
}
