package ec.com.sofka.data.dto.accountDTO;

import java.math.BigDecimal;

public class AccountResponseDTO {
    private String id;
    private BigDecimal balance;
    private String accountNumber;
    private String owner;

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

    public AccountResponseDTO(String id, BigDecimal balance, String accountNumber, String owner) {
        this.id = id;
        this.balance = balance;
        this.accountNumber = accountNumber;
        this.owner = owner;
    }

    public AccountResponseDTO(BigDecimal balance, String accountNumber, String owner) {
        this.balance = balance;
        this.accountNumber = accountNumber;
        this.owner = owner;
    }
}
