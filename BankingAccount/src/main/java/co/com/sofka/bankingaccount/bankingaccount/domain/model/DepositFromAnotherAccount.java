package co.com.sofka.bankingaccount.bankingaccount.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class DepositFromAnotherAccount extends Transaction {
    public DepositFromAnotherAccount(BigDecimal amount, String accountId) {
        super(amount, accountId, "DEPOSIT_FROM_ANOTHER_ACCOUNT");
        this.setCost(BigDecimal.valueOf(1.5));
    }

    public DepositFromAnotherAccount(String id, BigDecimal amount, String accountId) {
        super(amount, accountId, "DEPOSIT_FROM_ANOTHER_ACCOUNT");
        this.setId(id);
        this.setCost(BigDecimal.valueOf(1.5));
    }

    @Override
    public BigDecimal calculateImpact() {
        return getAmount().subtract(getCost());
    }
}
