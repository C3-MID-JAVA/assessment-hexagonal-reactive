package co.com.sofka.bankingaccount.bankingaccount.infrastructure.drivenAdapters.mongoRepository.entities.transaction.impl;

import co.com.sofka.bankingaccount.bankingaccount.infrastructure.drivenAdapters.mongoRepository.entities.transaction.TransactionEntity;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document(collection = "transactions")
@NoArgsConstructor
@TypeAlias("ATM_WITHDRAWAL")
public class AtmWithdrawalEntity extends TransactionEntity {
    public AtmWithdrawalEntity(BigDecimal amount, String accountId){
        super(amount.multiply(BigDecimal.valueOf(-1)), accountId, "ATM_WITHDRAWAL");
        this.setCost(BigDecimal.ONE); //Established Cost
    }

    @Override
    public BigDecimal calculateImpact(){
        return getAmount().subtract(getCost());
    }
}
