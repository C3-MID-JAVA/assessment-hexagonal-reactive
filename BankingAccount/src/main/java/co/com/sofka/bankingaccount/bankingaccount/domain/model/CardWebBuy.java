package co.com.sofka.bankingaccount.bankingaccount.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class CardWebBuy extends Transaction {
    public CardWebBuy(BigDecimal amount, String accountId){
        super(amount.multiply(BigDecimal.valueOf(-1)), accountId, "CARD_WEB_BUY");
        this.setCost(BigDecimal.valueOf(5)); //Value for secure
    }

    public CardWebBuy(String id, BigDecimal amount, String accountId) {
        super(amount, accountId, "CARD_WEB_BUY");
        this.setId(id);
        this.setCost(BigDecimal.valueOf(5));
    }

    @Override
    public BigDecimal calculateImpact(){
        return getAmount().subtract(getCost());
    }
}
