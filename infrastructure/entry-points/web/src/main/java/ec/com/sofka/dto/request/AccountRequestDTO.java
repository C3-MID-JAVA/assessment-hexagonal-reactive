package ec.com.sofka.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;

/**
 * DTO for account creation requests.
 */
@Schema(description = "Request DTO for creating a new account.")
public final class AccountRequestDTO {

  @Schema(description = "Unique account number", example = "1234567890")
  @NotBlank(message = "Account number is required.")
  @Pattern(regexp = "^[0-9]{10}$", message = "Account number must be a 10-digit numeric string.")
  private final String number;

  @Schema(description = "Initial balance of the account", example = "100.50")
  @NotNull(message = "Initial balance is required.")
  @DecimalMin(value = "0.01", inclusive = false, message = "Initial balance must be positive.")
  private final BigDecimal initialBalance;

  public AccountRequestDTO(String number, BigDecimal initialBalance) {
    this.number = number;
    this.initialBalance = initialBalance;
  }

  public String getNumber() {
    return number;
  }

  public BigDecimal getInitialBalance() {
    return initialBalance;
  }
}
