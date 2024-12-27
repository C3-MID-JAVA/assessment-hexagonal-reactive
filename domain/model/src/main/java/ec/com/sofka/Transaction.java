package ec.com.sofka;

import java.math.BigDecimal;

public class Transaction {
    private String id;

    private BigDecimal amount;

    private String type;

    private BigDecimal cost;

    private String idAccount;

    public Transaction() {
    }

    public Transaction(String id, BigDecimal amount, String type, BigDecimal cost, String idAccount) {
        this.id = id;
        this.amount = amount;
        this.type = type;
        this.cost = cost;
        this.idAccount = idAccount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(String idAccount) {
        this.idAccount = idAccount;
    }
}
