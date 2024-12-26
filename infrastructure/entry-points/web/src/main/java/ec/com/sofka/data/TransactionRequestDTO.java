package ec.com.sofka.data;

import ec.com.sofka.enums.TransactionType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Schema(description = "Request body for creating an account")
public class TransactionRequestDTO {

    @NotNull
    @Size(min = 10, max = 10, message = "The account number must be exactly 10 characters")
    @Schema(description = "The account number associated with the transaction (exactly 10 digits)", example = "0123456789")

    private String accountNumber;

    @NotNull
    @Positive(message = "The amount must be breater than zero")
    @Schema(description = "The amount to be transacted", example = "500.75")
    private BigDecimal amount;

    @NotNull
    @Schema(description = "The type of the transaction (e.g., deposit, withdrawal)", example = "BRANCH_DEPOSIT")
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
