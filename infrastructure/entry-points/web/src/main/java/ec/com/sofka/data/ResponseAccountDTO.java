package ec.com.sofka.data;

import java.math.BigDecimal;

public class ResponseAccountDTO {
    public String id;
    public String accountOwner;
    public BigDecimal balance;

    public ResponseAccountDTO(
            String id,
            String accountOwner,
            BigDecimal balance
    ) {
        this.id = id;
        this.accountOwner = accountOwner ;
        this.balance = balance;
    }

    public String getAccountOwner() {
        return accountOwner;
    }

    public void setAccountOwner(String accountOwner) {
        this.accountOwner = accountOwner;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
