package co.com.sofka.bankingaccount.bankingaccount.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class BranchDeposit extends Transaction {
    public BranchDeposit(BigDecimal amount, String accountId){
        super(amount,accountId,"BRANCH_DEPOSIT");
        this.setCost(BigDecimal.ZERO); //Without Cost
    }

    public BranchDeposit(String id, BigDecimal amount, String accountId) {
        super(amount, accountId, "BRANCH_DEPOSIT");
        this.setId(id);
        this.setCost(BigDecimal.valueOf(0));
    }

    @Override
    public BigDecimal calculateImpact(){
        return getAmount();
    }
}
