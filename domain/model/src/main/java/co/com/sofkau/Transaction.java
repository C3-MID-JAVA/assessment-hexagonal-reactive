package co.com.sofkau;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public abstract class Transaction {

    private String id;
    private String description;
    private BigDecimal amount;
    private String transactionType;
    private BigDecimal transactionFee;
    private LocalDateTime timestamp;
    private Account account;
    private Card card;

    public Transaction(Card card, String id, BigDecimal amount, String description, String transactionType, BigDecimal transactionFee, LocalDateTime timestamp, Account account) {
        this.card = card;
        this.id = id;
        this.amount = amount;
        this.description = description;
        this.transactionType = transactionType;
        this.transactionFee = transactionFee;
        this.timestamp = timestamp;
        this.account = account;
    }

    public Transaction() {
    }

    public abstract void processTransaction();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public BigDecimal getTransactionFee() {
        return transactionFee;
    }

    public void setTransactionFee(BigDecimal transactionFee) {
        this.transactionFee = transactionFee;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
