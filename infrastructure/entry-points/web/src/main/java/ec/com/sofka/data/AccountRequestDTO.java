package ec.com.sofka.data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class AccountRequestDTO {

    @NotNull
    @Size(min = 10, max = 10, message = "The account number must be exactly 10 characters")
    private String accountNumber;

    @NotNull
    @PositiveOrZero(message = "The initial balance must be grater than or equal to zero")
    private BigDecimal initialBalance;

    @NotNull
    @NotBlank(message = "The owner must not be empty")
    private String owner;

    public AccountRequestDTO(String accountNumber, BigDecimal initialBalance, String owner) {
        this.accountNumber = accountNumber;
        this.initialBalance = initialBalance;
        this.owner = owner;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(BigDecimal initialBalance) {
        this.initialBalance = initialBalance;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
