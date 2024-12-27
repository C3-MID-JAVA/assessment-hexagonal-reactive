package co.com.sofka.bankingaccount.bankingaccount.infrastructure.drivenAdapters.mongoRepository.entities.transaction.impl;

import co.com.sofka.bankingaccount.bankingaccount.infrastructure.drivenAdapters.mongoRepository.entities.transaction.TransactionEntity;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document(collection = "transactions")
@NoArgsConstructor
@TypeAlias("CARD_PHYSICAL_BUY")
public class CardPhysicalBuyEntity extends TransactionEntity {
    public CardPhysicalBuyEntity(BigDecimal amount, String accountId){
        super(amount, accountId, "CARD_PHYSICAL_BUY");
        this.setCost(BigDecimal.ZERO);
    }

    @Override
    public BigDecimal calculateImpact(){
        return getAmount();
    }
}
