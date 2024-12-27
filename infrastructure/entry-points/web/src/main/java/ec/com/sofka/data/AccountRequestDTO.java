package ec.com.sofka.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.math.BigDecimal;

@Schema(description = "Object representing the data to create a account.")
public class AccountRequestDTO implements Serializable {

    @Schema(description = "Number of account", example = "2200000000")
    @NotNull(message = "number cannot be null")
    @Pattern(regexp = "^[0-9]+$", message = "Incorrect number format")
    @Size(min = 10, max = 10, message = "Incorrect number length")
    private String number;

    @Schema(description = "Available balance of account", example = "200")
    @NotNull(message = "availableBalance cannot be null")
    private BigDecimal availableBalance;

    @Schema(description = "Retained balance of account", example = "10")
    @NotNull(message = "retainedBalance cannot be null")
    private BigDecimal retainedBalance;

    @Schema(description = "Status of account", example = "ACTIVE")
    @NotNull(message = "status cannot be null")
    @Pattern(regexp = "^(ACTIVE|INACTIVE)$", message = "Incorrect status")
    private String status;

    @Schema(description = "Account User ID")
    @NotNull(message = "userId cannot be null")
    private String userId;

    public AccountRequestDTO(String number, BigDecimal availableBalance, BigDecimal retainedBalance, String status, String userId) {
        this.number = number;
        this.availableBalance = availableBalance;
        this.retainedBalance = retainedBalance;
        this.status = status;
        this.userId = userId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public BigDecimal getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(BigDecimal availableBalance) {
        this.availableBalance = availableBalance;
    }

    public BigDecimal getRetainedBalance() {
        return retainedBalance;
    }

    public void setRetainedBalance(BigDecimal retainedBalance) {
        this.retainedBalance = retainedBalance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}