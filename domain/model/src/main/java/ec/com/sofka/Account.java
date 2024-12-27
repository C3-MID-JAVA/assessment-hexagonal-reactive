package ec.com.sofka;


import java.math.BigDecimal;
import java.util.List;

public class Account {
    private int id;
    private String accountNumber;
    private String accountType;
    private BigDecimal balance;
    private List<Card> cards;
    private List<Transaction> transactions;

    public Account(String accountNumber, String accountType, BigDecimal balance, List<Card> cards, int id, List<Transaction> transactions) {
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.balance = balance;
        this.cards = cards;
        this.id = id;
        this.transactions = transactions;
    }

    public Account() {
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
