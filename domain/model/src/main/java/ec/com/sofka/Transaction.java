package ec.com.sofka;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {
    private String id;
    private TransactionType transactionType;
    private BigDecimal amount;
    private BigDecimal fee;
    private LocalDateTime date;
    private String description;
    private Account account;

    public Transaction() {}

    public Transaction(String id, TransactionType transactionType, BigDecimal amount, BigDecimal fee, LocalDateTime date, String description, Account account) {
        this.id = id;
        this.transactionType = transactionType;
        this.amount = amount;
        this.fee = fee;
        this.date = date;
        this.description = description;
        this.account = account;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
