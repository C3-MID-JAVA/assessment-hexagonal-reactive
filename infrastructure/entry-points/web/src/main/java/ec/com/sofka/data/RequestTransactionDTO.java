package ec.com.sofka.data;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.math.BigDecimal;

public class RequestTransactionDTO {

    private String type;
    private BigDecimal amount;
    private BigDecimal transactionCost;
    private String bankAccountId; // Solo enviamos el ID de la cuenta bancaria asociada

    public RequestTransactionDTO() {
    }

    @JsonCreator
    public RequestTransactionDTO(String type, BigDecimal amount, BigDecimal transactionCost, String bankAccountId) {
        this.type = type;
        this.amount = amount;
        this.transactionCost = transactionCost;
        this.bankAccountId = bankAccountId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getTransactionCost() {
        return transactionCost;
    }

    public void setTransactionCost(BigDecimal transactionCost) {
        this.transactionCost = transactionCost;
    }

    public String getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(String bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

}
