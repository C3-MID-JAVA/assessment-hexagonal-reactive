package co.com.sofka.bankingaccount.bankingaccount.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class AtmWithdrawal extends Transaction {
    public AtmWithdrawal(BigDecimal amount, String accountId){
        super(amount.multiply(BigDecimal.valueOf(-1)), accountId, "ATM_WITHDRAWAL");
        this.setCost(BigDecimal.ONE); //Established Cost
    }
    public AtmWithdrawal(String id, BigDecimal amount, String accountId) {
        super(amount, accountId, "ATM_WITHDRAWAL");
        this.setId(id);
        this.setCost(BigDecimal.ONE);
    }

    @Override
    public BigDecimal calculateImpact(){
        return getAmount().subtract(getCost());
    }
}
