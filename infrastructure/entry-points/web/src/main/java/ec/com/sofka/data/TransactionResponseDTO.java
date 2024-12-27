package ec.com.sofka.data;

import ec.com.sofka.Account;
import ec.com.sofka.TransactionType;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionResponseDTO {
    private String id;

    private Account account;;

    @NotNull
    private BigDecimal fee;

    private BigDecimal amount;

    private TransactionType transactionType;

    private LocalDateTime date;

    private String description;


    public TransactionResponseDTO(String id, Account account, BigDecimal fee, BigDecimal amount, TransactionType transactionType, LocalDateTime date, String description) {
        this.id = id;
        this.account = account;
        this.fee = fee;
        this.amount = amount;
        this.transactionType = transactionType;
        this.date = date;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
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
}
