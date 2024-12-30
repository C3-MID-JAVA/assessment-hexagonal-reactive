package ec.com.sofka.data;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class AccountInDTO {


    @NotBlank(message = "El número de cuenta no puede estar vacío.")
    @Size(min = 10, max = 20, message = "El número de cuenta debe tener entre 10 y 20 caracteres.")
    private String accountNumber;

    @NotNull(message = "El balance no puede ser nulo.")
    @DecimalMin(value = "0.0", inclusive = true, message = "El balance no puede ser negativo.")
    private BigDecimal balance;

    @NotBlank(message = "El ID del cliente no puede estar vacío.")
    private String custumerId;

    private String cardId;

    public AccountInDTO() {
    }

    public AccountInDTO(String accountNumber, BigDecimal balance, String custumerId, String cardId) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.custumerId = custumerId;
        this.cardId = cardId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getCustumerId() {
        return custumerId;
    }

    public void setCustumerId(String custumerId) {
        this.custumerId = custumerId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }
}
