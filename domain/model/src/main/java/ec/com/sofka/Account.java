package ec.com.sofka;




import com.fasterxml.jackson.annotation.JsonCreator;

import java.math.BigDecimal;

public class Account {
    private String id;
    private BigDecimal balance;
    private String owner;

    @JsonCreator
    public Account(String id,
                   BigDecimal balance,
                   String owner)
    {
        this.id = id;
        this.balance = balance;
        this.owner = owner;
    }

    @JsonCreator
    public Account(String bankAccountId) {
        this.id = bankAccountId;
    }

    public String getId() {return id;}

    public void setId(String id) {this.id = id;}

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
