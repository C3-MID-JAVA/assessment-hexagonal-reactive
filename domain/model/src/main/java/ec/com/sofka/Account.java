package ec.com.sofka;

import java.math.BigDecimal;

public class Account {
  private String id;
  private String accountNumber;
  private BigDecimal balance;

  private Account(String id, String accountNumber, BigDecimal balance) {
    this.id = id;
    this.accountNumber = accountNumber;
    this.balance = balance;
  }

  public static Account create(String id, String accountNumber, BigDecimal balance) {
    if (accountNumber == null || accountNumber.isBlank()) {
      throw new IllegalArgumentException("Account number cannot be null or blank");
    }
    if (balance == null || balance.compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalArgumentException("Balance must be non-negative");
    }
    return new Account(id, accountNumber, balance);
  }

  public String getId() {
    return id;
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public void setBalance(BigDecimal balance) {
    this.balance = balance;
  }
}
