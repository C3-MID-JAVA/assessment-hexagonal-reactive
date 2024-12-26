package ec.com.sofka.strategy;


import ec.com.sofka.Account;
import ec.com.sofka.ConflictException;

import java.math.BigDecimal;

public class OnlinePurchaseStrategy implements TransaccionStrategy {
    private static final BigDecimal AMOUNT = BigDecimal.valueOf(5.0);

    @Override
    public void validate(Account account, BigDecimal amount) throws ConflictException {
        if (account.getBalance().compareTo(amount.add(AMOUNT)) < 0) {
            throw new ConflictException("Insufficient balance for online purchase");
        }
    }

    @Override
    public BigDecimal getAmount() {
        return AMOUNT;
    }
}
