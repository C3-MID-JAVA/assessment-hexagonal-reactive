package ec.com.sofka.strategy;

import ec.com.sofka.Account;
import ec.com.sofka.ConflictException;

import java.math.BigDecimal;

public class ATMWithDrawalStrategy implements TransaccionStrategy{
    private static final BigDecimal COSTO = BigDecimal.valueOf(1.0);

    @Override
    public void validate(Account account, BigDecimal amount) {
        if (account.getBalance().compareTo(amount.add(COSTO)) < 0) {
            throw new ConflictException("Insufficient balance for ATM withdrawal");
        }
    }

    @Override
    public BigDecimal getAmount() {
        return COSTO;
    }
}
