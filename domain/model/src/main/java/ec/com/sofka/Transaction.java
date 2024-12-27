package ec.com.sofka;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {
    private String id;
    private String type; // "DEPOSIT", "WITHDRAWAL", "PURCHASE"
    private BigDecimal amount;
    private BigDecimal transactionCost;
    private LocalDateTime timestamp;
    private Account bankAccount;

    // Constructor con argumentos
    public Transaction(
                       String id,
                       String type,
                       BigDecimal amount,
                       BigDecimal transactionCost,
                       Account bankAccount) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.transactionCost = transactionCost;
        this.bankAccount = bankAccount;
        this.timestamp = LocalDateTime.now();
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getTransactionCost() {
        return transactionCost;
    }

    public void setTransactionCost(BigDecimal transactionCost) {
        this.transactionCost = transactionCost;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Account getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(Account bankAccount) {
        this.bankAccount = bankAccount;
    }
}
