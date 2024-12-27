package co.com.sofka.bankingaccount.bankingaccount.infrastructure.drivenAdapters.mongoRepository.entities.transaction.impl;

import co.com.sofka.bankingaccount.bankingaccount.infrastructure.drivenAdapters.mongoRepository.entities.transaction.TransactionEntity;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document(collection = "transactions")
@NoArgsConstructor
@TypeAlias("BRANCH_DEPOSIT")
public class BranchDepositEntity extends TransactionEntity {
    public BranchDepositEntity(BigDecimal amount, String accountId){
        super(amount,accountId,"BRANCH_DEPOSIT");
        this.setCost(BigDecimal.ZERO); //Without Cost
    }

    @Override
    public BigDecimal calculateImpact(){
        return getAmount();
    }
}
