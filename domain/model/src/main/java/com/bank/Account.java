package com.bank;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Account {
    private String id;

    private String holder;

    private BigDecimal balance;

    private List<Operation> operations = new ArrayList<>();

    public Account() {}

    public Account(String id) {
        this.id = id;
    }

    public Account(String id, String holder, BigDecimal balance) {
        this.id = id;
        this.holder = holder;
        this.balance = balance;
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

    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }
}