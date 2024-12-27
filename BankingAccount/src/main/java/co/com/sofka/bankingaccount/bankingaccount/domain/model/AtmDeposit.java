package co.com.sofka.bankingaccount.bankingaccount.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class AtmDeposit extends Transaction {
    public AtmDeposit(BigDecimal amount, String accountId){
        super(amount, accountId, "ATM_DEPOSIT");
        this.setCost(BigDecimal.valueOf(2)); //Established Cost 2$
    }

    public AtmDeposit(String id, BigDecimal amount, String accountId) {
        super(amount, accountId, "ATM_DEPOSIT");
        this.setId(id);
        this.setCost(BigDecimal.valueOf(2));
    }

    @Override
    public BigDecimal calculateImpact(){
        return getAmount().subtract(getCost());
    }
}
