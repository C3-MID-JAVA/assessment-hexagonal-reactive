package ec.com.sofka.mapper;

import ec.com.sofka.Account;
import ec.com.sofka.data.AccountEntity;

public class AccountMapper {

    public static Account accountEntityToAccount(AccountEntity accountEntity) {
        return new Account(
                accountEntity.getId(),accountEntity.getBalance(),accountEntity.getAccountNumber(),accountEntity.getOwner()
        );
    }

    public static AccountEntity accountToAccountEntity(Account account) {
        return new AccountEntity(
                account.getId(),account.getBalance(),account.getOwner(),account.getAccountNumber()
        );
    }
}

