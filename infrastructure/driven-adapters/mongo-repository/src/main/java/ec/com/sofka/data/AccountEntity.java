package ec.com.sofka.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.util.List;

@Document(collection = "account")
public class AccountEntity {

    @Id
    private String id;
    private BigDecimal balance;
    private String accountNumber;
    private String owner;

    public AccountEntity() {
    }

    public AccountEntity(String id, BigDecimal balance, String accountNumber, String owner, List<TransactionEntity> transacciones) {
        this.id = id;
        this.balance = balance;
        this.accountNumber = accountNumber;
        this.owner = owner;
        this.transacciones = transacciones;
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

    public List<TransactionEntity> getTransacciones() {
        return transacciones;
    }

    public void setTransacciones(List<TransactionEntity> transacciones) {
        this.transacciones = transacciones;
    }

    private List<TransactionEntity> transacciones;
}
