package ec.com.sofka.dto.response;

import java.math.BigDecimal;

/**
 * DTO for transaction responses.
 */
public final class TransactionResponseDTO {

  private final String transactionType;
  private final BigDecimal transactionAmount;
  private final BigDecimal transactionCost;

  public TransactionResponseDTO(String transactionType, BigDecimal transactionAmount, BigDecimal transactionCost) {
    this.transactionType = transactionType;
    this.transactionAmount = transactionAmount;
    this.transactionCost = transactionCost;
  }

  public String getTransactionType() {
    return transactionType;
  }

  public BigDecimal getTransactionAmount() {
    return transactionAmount;
  }

  public BigDecimal getTransactionCost() {
    return transactionCost;
  }

}