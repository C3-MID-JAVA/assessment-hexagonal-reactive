package ec.com.sofka.mapper;

import ec.com.sofka.Account;
import ec.com.sofka.data.AccountEntity;

public class AccountModelMapper {

  public static Account fromEntity(AccountEntity accountEntity) {
    return Account.create(
            accountEntity.getId(),
            accountEntity.getAccountNumber(),
            accountEntity.getBalance());
  }

  public static AccountEntity toEntity(Account account) {
    return new AccountEntity(
            account.getId(),
            account.getAccountNumber(),
            account.getBalance());
  }
}