package ec.com.sofka.data;

import ec.com.sofka.Account;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ResponseTransactionDTO {

    private String id;
    private String type;
    private BigDecimal amount;
    private BigDecimal transactionCost;
    private Account bankAccount; // Solo devolvemos el ID de la cuenta bancaria asociada
    private LocalDateTime timestamp;


    public ResponseTransactionDTO(
            String id,
            String type,
            BigDecimal amount,
            BigDecimal transactionCost,
            Account account,
            LocalDateTime timestamp) {

        this.id = id;
        this.type = type;
        this.amount = amount;
        this.transactionCost = transactionCost;
        this.bankAccount = account;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBankAccount(Account bankAccount) {
        this.bankAccount = bankAccount;
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

    public Account getBankAccount() {
        return bankAccount;
    }

    public void setBankAccountId(Account account) {
        this.bankAccount = account;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

}
