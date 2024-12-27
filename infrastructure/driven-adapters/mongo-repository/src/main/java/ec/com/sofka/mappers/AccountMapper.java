package ec.com.sofka.mappers;

import ec.com.sofka.Account;
import ec.com.sofka.data.AccountEntity;

import java.util.ArrayList;

public class AccountMapper {
    Account account = new Account();

    // Convertir de Cuenta a CuentaDTO
    public static Account toModel(AccountEntity accountEntity) {
        Account account = new Account();
        account.setId(accountEntity.getId());
        account.setBalance(accountEntity.getBalance());
        account.setAccountNumber(accountEntity.getAccountNumber());
        account.setOwner(accountEntity.getOwner());
        return account;
    }

    public static AccountEntity toDocument(Account account) {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setId(account.getId());
        accountEntity.setBalance(account.getBalance());
        accountEntity.setAccountNumber(account.getAccountNumber());
        accountEntity.setOwner(account.getOwner());
        return accountEntity;
    }


}
