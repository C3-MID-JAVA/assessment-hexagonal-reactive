package ec.com.sofka;

import java.math.BigDecimal;

public class Account {
    private String id;

    private String number;

    private BigDecimal availableBalance;

    private BigDecimal retainedBalance;

    private String status;

    private User user;

    public Account() {}

    public Account(String id, String number, BigDecimal availableBalance, BigDecimal retainedBalance, String status, User user) {
        this.id = id;
        this.number = number;
        this.availableBalance = availableBalance;
        this.retainedBalance = retainedBalance;
        this.status = status;
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public BigDecimal getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(BigDecimal availableBalance) {
        this.availableBalance = availableBalance;
    }

    public BigDecimal getRetainedBalance() {
        return retainedBalance;
    }

    public void setRetainedBalance(BigDecimal retainedBalance) {
        this.retainedBalance = retainedBalance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}