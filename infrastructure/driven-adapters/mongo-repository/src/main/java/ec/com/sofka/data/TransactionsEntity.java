package ec.com.sofka.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "transactions")
public class TransactionsEntity {

    @Id
    private String id;

    private String type; // "DEPOSIT", "WITHDRAWAL", "PURCHASE"
    private BigDecimal amount;
    private BigDecimal transactionCost;
    private LocalDateTime timestamp;

    @DBRef // Referencia a otra colección
    private AccountEntity bankAccount;

    // Constructor con todos los argumentos
    public TransactionsEntity(
            String type,
            BigDecimal amount,
            BigDecimal transactionCost,
            AccountEntity bankAccount) {
        this.id = UUID.randomUUID().toString();
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

    public AccountEntity getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(AccountEntity bankAccount) {
        this.bankAccount = bankAccount;
    }


}
