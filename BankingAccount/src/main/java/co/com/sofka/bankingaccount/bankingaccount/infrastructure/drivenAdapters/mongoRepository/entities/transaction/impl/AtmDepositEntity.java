package co.com.sofka.bankingaccount.bankingaccount.infrastructure.drivenAdapters.mongoRepository.entities.transaction.impl;

import co.com.sofka.bankingaccount.bankingaccount.infrastructure.drivenAdapters.mongoRepository.entities.transaction.TransactionEntity;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document(collection = "transactions")
@NoArgsConstructor
@TypeAlias("ATM_DEPOSIT")
public class AtmDepositEntity extends TransactionEntity {
    public AtmDepositEntity(BigDecimal amount, String accountId){
        super(amount, accountId, "ATM_DEPOSIT");
        this.setCost(BigDecimal.valueOf(2)); //Established Cost 2$
    }

    @Override
    public BigDecimal calculateImpact(){
        return getAmount().subtract(getCost());
    }
}
