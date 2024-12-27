package ec.com.sofka.data;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class AccountRequestDTO {

    @NotBlank(message = "Account number cannot be blank")
    @Pattern(regexp = "^\\d{7}$", message = "Account number must be 7 digits")
    private String accountNumber;

    @NotNull
    @NotEmpty(message = "Account holder is required")
    private String accountHolder;

    @PositiveOrZero(message = "Global balance must be a positive number")
    private BigDecimal balance;

   /* public AccountRequestDTO(){

    }*/

    public AccountRequestDTO(String accountNumber, String accountHolder, BigDecimal balance) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = balance;
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

    public void setAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
