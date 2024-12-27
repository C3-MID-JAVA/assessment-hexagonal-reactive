package ec.com.sofka;

import java.math.BigDecimal;

public class Account {
    private String id;
    private String accountNumber;
    private BigDecimal balance;
    private String userId;

    public Account(String userId) {
        this.userId = userId;
    }

    public Account(String id, String accountNumber, BigDecimal balance, String userId) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
