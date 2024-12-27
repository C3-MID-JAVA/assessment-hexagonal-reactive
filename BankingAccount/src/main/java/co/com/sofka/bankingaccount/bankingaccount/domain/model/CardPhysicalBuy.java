package co.com.sofka.bankingaccount.bankingaccount.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class CardPhysicalBuy extends Transaction {
    public CardPhysicalBuy(BigDecimal amount, String accountId){
        super(amount, accountId, "CARD_PHYSICAL_BUY");
        this.setCost(BigDecimal.ZERO);
    }

    public CardPhysicalBuy(String id, BigDecimal amount, String accountId) {
        super(amount, accountId, "CARD_PHYSICAL_BUY");
        this.setId(id);
        this.setCost(BigDecimal.valueOf(0));
    }

    @Override
    public BigDecimal calculateImpact(){
        return getAmount();
    }
}
