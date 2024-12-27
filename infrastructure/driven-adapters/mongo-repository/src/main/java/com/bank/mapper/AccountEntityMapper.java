package com.bank.mapper;

import com.bank.Account;
import com.bank.data.AccountEntity;

import java.util.ArrayList;

public class AccountEntityMapper {
    public static AccountEntity toAccountEntity(Account acc) {
        AccountEntity entity = new AccountEntity();

        entity.setId(acc.getId());
        entity.setHolder(acc.getHolder());
        entity.setBalance(acc.getBalance());
        entity.setOperations(new ArrayList<>());

        return entity;
    }

    public static Account toAccount(AccountEntity entity) {
        Account acc = new Account();

        acc.setId(entity.getId());
        acc.setHolder(entity.getHolder());
        acc.setBalance(entity.getBalance());
        acc.setOperations(new ArrayList<>());

        return acc;
    }
}
