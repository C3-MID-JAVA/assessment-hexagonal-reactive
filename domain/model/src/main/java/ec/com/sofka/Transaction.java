package ec.com.sofka;

import java.math.BigDecimal;

public class Transaction {
  private String id;
  private String accountId;
  private String type;
  private BigDecimal amount;
  private BigDecimal transactionCost;

  private Transaction(String id, String accountId, String type, BigDecimal amount, BigDecimal transactionCost) {
    this.id = id;
    this.accountId = accountId;
    this.type = type;
    this.amount = amount;
    this.transactionCost = transactionCost;
  }

  public static Transaction create(String id, String accountId, String type, BigDecimal amount, BigDecimal transactionCost) {
    if (accountId == null || accountId.isBlank()) {
      throw new IllegalArgumentException("Account ID cannot be null or blank");
    }
    if (type == null || type.isBlank()) {
      throw new IllegalArgumentException("Transaction type cannot be null or blank");
    }
    if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
      throw new IllegalArgumentException("Transaction amount must be positive");
    }
    return new Transaction(id, accountId, type, amount, transactionCost);
  }

  public String getId() {
    return id;
  }

  public String getAccountId() {
    return accountId;
  }

  public String getType() {
    return type;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public BigDecimal getTransactionCost() {
    return transactionCost;
  }
}
