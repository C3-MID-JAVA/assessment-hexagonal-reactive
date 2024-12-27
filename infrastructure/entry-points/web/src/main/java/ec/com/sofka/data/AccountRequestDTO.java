package ec.com.sofka.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
@Schema(description = "Request body for creating an account")
public class AccountRequestDTO {

    @NotNull
    @Size(min = 10, max = 10, message = "The account number must be exactly 10 characters")
    @Schema(description = "The account number (exactly 10 digits)", example = "0123456789")
    private String accountNumber;

    @NotNull
    @PositiveOrZero(message = "The initial balance must be grater than or equal to zero")
    @Schema(description = "The initial balance of the account", example = "500")
    private BigDecimal initialBalance;

    @NotNull
    @NotBlank(message = "The owner must not be empty")
    @Schema(description = "The name of the account owner", example = "Anderson Zambrano")
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
