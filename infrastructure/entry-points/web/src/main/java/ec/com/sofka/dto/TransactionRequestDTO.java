package ec.com.sofka.dto;

import ec.com.sofka.TransactionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class TransactionRequestDTO {

    @Positive(message = "Amount must be greater than 0")
    private BigDecimal amount;

    @NotNull(message = "Type is required")
    private TransactionType type;

    @NotBlank(message = "Account number cannot be blank")
    private String accountNumber;

    public TransactionRequestDTO(BigDecimal amount, TransactionType type, String accountNumber) {
        this.amount = amount;
        this.type = type;
        this.accountNumber = accountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
