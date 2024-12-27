package ec.com.sofka.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;

@Document(collection = "bank_account")
public class AccountEntity {
    @Id
    private String id;

    @Field("account_number")
    private String accountNumber;

    @Field("account_holder")
    private String accountHolder;

    @Field("global_balance")
    private BigDecimal balance;


    public AccountEntity(String id, BigDecimal balance, String accountHolder, String accountNumber) {
        this.id = id;
        this.balance = balance;
        this.accountHolder = accountHolder;
        this.accountNumber = accountNumber;
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

    public String getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(String owner) {
        this.accountHolder = owner;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}

