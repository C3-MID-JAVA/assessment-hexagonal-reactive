package com.bank.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "accounts")
public class AccountEntity {
    @Id
    private String id;

    private String holder;

    private BigDecimal balance;

    @DBRef
    private List<OperationEntity> operations;

    public AccountEntity() {}

    public AccountEntity(String id) {
        this.id = id;
    }

    public AccountEntity(String id, String holder, BigDecimal balance, List<OperationEntity> operations) {
        this.id = id;
        this.holder = holder;
        this.balance = balance;
        this.operations = new ArrayList<>(operations);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public List<OperationEntity> getOperations() {
        return operations;
    }

    public void setOperations(List<OperationEntity> operations) {
        this.operations = operations;
    }
}