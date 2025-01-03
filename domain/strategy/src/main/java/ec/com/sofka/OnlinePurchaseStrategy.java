package ec.com.sofka;

import java.math.BigDecimal;
import org.springframework.stereotype.Component;

@Component
public class OnlinePurchaseStrategy implements ITransactionCostStrategy {

  @Override
  public BigDecimal calculateCost(BigDecimal amount) {
    return BigDecimal.valueOf(5.00); // Fixed fee for online purchases
  }

  @Override
  public TRANSACTION_TYPE getType() {
    return TRANSACTION_TYPE.ONLINE_PURCHASE;
  }
}
