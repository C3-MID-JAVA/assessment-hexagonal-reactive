package ec.com.sofka.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.util.Date;

@Document("transactions")
public class TransactionEntity {
    @Id
    private String id;

    @Field(name = "account_number")
    private String accountNumber;

    @Field(name = "transaction_type_identify")
    private String transactionTypeIdentify;

    @Field(name = "details")
    private String details;

    @Field(name = "value")
    private BigDecimal value;

    @Field(name = "date")
    private Date date;

    @Field(name = "status")
    private String status;

    @Field(name = "account")
    private AccountEntity account;

    @Field(name = "transaction_type")
    private TransactionTypeEntity transactionType;

    public TransactionEntity() {
    }

    public TransactionEntity(String id, String accountNumber, String transactionTypeIdentify, String details, BigDecimal value, Date date, String status, AccountEntity accountEntity, TransactionTypeEntity transactionTypeEntity) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.transactionTypeIdentify = transactionTypeIdentify;
        this.details = details;
        this.value = value;
        this.date = date;
        this.status = status;
        this.account = accountEntity;
        this.transactionType = transactionTypeEntity;
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

    public String getTransactionTypeIdentify() {
        return transactionTypeIdentify;
    }

    public void setTransactionTypeIdentify(String transactionTypeIdentify) {
        this.transactionTypeIdentify = transactionTypeIdentify;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public AccountEntity getAccount() {
        return account;
    }

    public void setAccount(AccountEntity accountEntity) {
        this.account = accountEntity;
    }

    public TransactionTypeEntity getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionTypeEntity transactionTypeEntity) {
        this.transactionType = transactionTypeEntity;
    }
}