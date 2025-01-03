package ec.com.sofka.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;

/**
 * DTO for transaction requests.
 */
@Schema(description = "Request DTO for processing a new transaction.")
public final class TransactionRequestDTO {

  @Schema(description = "ID of the account associated with the transaction", example = "a1b2c3d4e5f6")
  @NotBlank(message = "Account ID is required.")
  @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Account ID must be alphanumeric.")
  private final String accountId;

  @Schema(description = "Type of the transaction (e.g., DEPOSIT, WITHDRAWAL)", example = "ATM_DEPOSIT")
  @NotBlank(message = "Transaction type is required.")
  @Pattern(regexp = "^[A-Z_]+$", message = "Transaction type must be in uppercase and snake_case format.")
  private final String transactionType;

  @Schema(description = "Amount for the transaction", example = "50.00")
  @NotNull(message = "Transaction amount is required.")
  @DecimalMin(value = "0.01", inclusive = false, message = "Transaction amount must be positive.")
  private final BigDecimal transactionAmount;

  public TransactionRequestDTO(String accountId, String transactionType, BigDecimal transactionAmount) {
    this.accountId = accountId;
    this.transactionType = transactionType;
    this.transactionAmount = transactionAmount;
  }

  public String getAccountId() {
    return accountId;
  }

  public String getTransactionType() {
    return transactionType;
  }

  public BigDecimal getTransactionAmount() {
    return transactionAmount;
  }
}
