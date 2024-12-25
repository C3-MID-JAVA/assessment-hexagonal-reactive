package ec.com.sofka.data;

import ec.com.sofka.enums.TransactionType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class TransactionRequestDTO {

    @NotNull
    @Size(min = 10, max = 10, message = "The account number must be exactly 10 characters")
    private String accountNumber;

    @NotNull
    @Positive(message = "The amount must be breater than zero")
    private BigDecimal amount;

    @NotNull
    private TransactionType transactionType;

    public TransactionRequestDTO(String accountNumber, BigDecimal amount, TransactionType transactionType) {
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.transactionType = transactionType;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }
}
