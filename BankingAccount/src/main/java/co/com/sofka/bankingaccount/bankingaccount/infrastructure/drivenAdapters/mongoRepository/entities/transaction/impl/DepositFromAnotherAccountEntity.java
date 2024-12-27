package co.com.sofka.bankingaccount.bankingaccount.infrastructure.drivenAdapters.mongoRepository.entities.transaction.impl;

import co.com.sofka.bankingaccount.bankingaccount.infrastructure.drivenAdapters.mongoRepository.entities.transaction.TransactionEntity;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document(collection = "transactions")
@NoArgsConstructor
@TypeAlias("DEPOSIT_FROM_ANOTHER_ACCOUNT")
public class DepositFromAnotherAccountEntity extends TransactionEntity {

    public DepositFromAnotherAccountEntity(BigDecimal amount, String accountId) {
        super(amount, accountId, "DEPOSIT_FROM_ANOTHER_ACCOUNT");
        this.setCost(BigDecimal.valueOf(1.5));
    }

    @Override
    public BigDecimal calculateImpact() {
        return getAmount().subtract(getCost());
    }
}
