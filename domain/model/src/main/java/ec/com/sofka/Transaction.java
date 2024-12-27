package ec.com.sofka;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {
    private int id;
    private Branch branch;
    private LocalDateTime date;
    private String type;
    private BigDecimal amount;
    private String description;
    private Account destinationAccount;
    private Account sourceAccount;

    public Transaction(BigDecimal amount, Branch branch, LocalDateTime date, String description, Account destinationAccount, int id, Account sourceAccount, String type) {
        this.amount = amount;
        this.branch = branch;
        this.date = date;
        this.description = description;
        this.destinationAccount = destinationAccount;
        this.id = id;
        this.sourceAccount = sourceAccount;
        this.type = type;
    }

    public Transaction() {
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
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

    public Account getDestinationAccount() {
        return destinationAccount;
    }

    public void setDestinationAccount(Account destinationAccount) {
        this.destinationAccount = destinationAccount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Account getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(Account sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
