package ec.com.sofka.data;

import ec.com.sofka.TransactionType;
//import jakarta.persistence.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Document(collection = "transaction")
public class TransactionEntity {

    @Id
    private String id;

//    @Enumerated(EnumType.STRING)
    @Field("transaction_type")
    private TransactionType transactionType;

    private BigDecimal amount;
    private BigDecimal fee;
    private LocalDateTime date;
    private String description;

    @DBRef
    @Field("bank_account_id")
    private AccountEntity account;

    public TransactionEntity() {}

    public TransactionEntity(String id, TransactionType transactionType, BigDecimal amount, BigDecimal fee, LocalDateTime date,
                       String description,
                       AccountEntity account) {
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

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public AccountEntity getAccount() {
        return account;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAccount(AccountEntity account) {
        this.account = account;
    }
}