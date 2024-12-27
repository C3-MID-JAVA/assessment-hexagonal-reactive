package ec.com.sofka.data;

import java.math.BigDecimal;

public class RequestAccountDTO {

    private String id;
    private String accountOwner;
    private BigDecimal initialBalance;

    public RequestAccountDTO(
            String accountOwner,
            BigDecimal initialBalance
    )
    {
        this.accountOwner = accountOwner;
        this.initialBalance = initialBalance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountOwner() {
        return accountOwner;
    }

    public void setAccountOwner(String accountOwner) {
        this.accountOwner = accountOwner;
    }

    public BigDecimal getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(BigDecimal initialBalance) {
        this.initialBalance = initialBalance;
    }


}
