package ec.com.sofka.data.dto.transactionDTO;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class TransactionRequestDTO {
    @NotNull(message = "El campo amount no debe ser nulo")
    @DecimalMin(value = "0.0", inclusive = false, message = "El amount debe ser mayor a 0.0")
    private BigDecimal amount;

    @NotNull(message = "El campo type no debe ser nulo")
    @Size(max = 50, message = "El campo type no debe tener más de 50 caracteres")
    private String type;

    @NotNull(message = "El campo cost no debe ser nulo")
    @DecimalMin(value = "0.0", message = "El cost debe ser mayor o igual a 0.0")
    private BigDecimal cost;

    private String idAccount; // ID referenciado de la cuenta

    public TransactionRequestDTO() {
    }

    public TransactionRequestDTO(BigDecimal amount, String type, BigDecimal cost, String idAccount) {
        this.amount = amount;
        this.type = type;
        this.cost = cost;
        this.idAccount = idAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(String idAccount) {
        this.idAccount = idAccount;
    }
}
