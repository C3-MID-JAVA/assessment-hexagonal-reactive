package ec.com.sofka.data.dto.accountDTO;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class AccountRequestDTO {

    private String id;
    @NotNull(message = "El campo balance no debe ser nulo")
    @DecimalMin(value = "0.0", message = "El balance debe ser mayor o igual a 0.0")
    private BigDecimal balance;

    @NotNull(message = "El campo accountNumber no debe ser nulo")
    @Size(min = 10, max = 10, message = "El campo accountNumber debe tener exactamente 10 dígitos")
    private String accountNumber;

    @NotNull(message = "El campo owner no debe ser nulo")
    @Size(max = 100, message = "El campo owner no debe tener más de 100 caracteres")
    private String owner;

    public AccountRequestDTO() {
    }

    public AccountRequestDTO(String id, BigDecimal balance, String accountNumber, String owner) {
        this.id = id;
        this.balance = balance;
        this.accountNumber = accountNumber;
        this.owner = owner;
    }

    public AccountRequestDTO(BigDecimal balance, String accountNumber, String owner) {
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

}
