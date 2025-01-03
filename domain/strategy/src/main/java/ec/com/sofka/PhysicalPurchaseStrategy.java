package ec.com.sofka;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PhysicalPurchaseStrategy implements ITransactionCostStrategy {

  @Override
  public BigDecimal calculateCost(BigDecimal amount) {
    return BigDecimal.ZERO; // No cost for physical purchases
  }

  @Override
  public TRANSACTION_TYPE getType() {
    return TRANSACTION_TYPE.PHYSICAL_PURCHASE;
  }
}
