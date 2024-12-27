package ec.com.sofka;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Account {
    private String id;
    private BigDecimal balance;
    private String accountNumber;
    private String owner;
    private List<Transaction> transactions= new ArrayList<>();

    public Account(String id, BigDecimal balance, String accountNumber,String owner) {
        this.id = id;
        this.balance = balance;
        this.owner = owner;
        this.accountNumber = accountNumber;
    }

    public Account(BigDecimal balance, String accountNumber, String owner) {
        this.balance = balance;
        this.accountNumber = accountNumber;
        this.owner = owner;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
