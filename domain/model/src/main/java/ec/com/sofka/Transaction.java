package ec.com.sofka;

import java.math.BigDecimal;
import java.util.Date;

public class Transaction {
    private String id;

    private String accountNumber;

    private String details;

    private BigDecimal value;

    private Date date;

    private String status;

    private Account account;

    private TransactionType transactionType;

    public Transaction(){}

    public Transaction(String id, String accountNumber, String details, BigDecimal value, Date date, String status, Account account, TransactionType transactionType) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.details = details;
        this.value = value;
        this.date = date;
        this.status = status;
        this.account = account;
        this.transactionType = transactionType;
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account accountEntity) {
        this.account = accountEntity;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }
}