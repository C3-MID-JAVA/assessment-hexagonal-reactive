package ec.com.sofka.mapper;

import ec.com.sofka.Account;
import ec.com.sofka.data.AccountEntity;

public class AccountMapper {
    public static Account toAccount(AccountEntity accountEntity) {
        return new Account(accountEntity.getId(), accountEntity.getBalance(),
                 accountEntity.getAccountHolder(), accountEntity.getAccountNumber());
    }
    public static AccountEntity toAccountEntity(Account account) {
        return new AccountEntity(account.getId(), account.getBalance(),
                account.getAccountHolder(), account.getAccountNumber());
    }
}
