package ec.com.sofka.mapper;

import ec.com.sofka.Account;
import ec.com.sofka.data.AccountEntity;
import org.springframework.stereotype.Component;

@Component
public class AccountEntityMapper {

    public static AccountEntity mapToEntity(Account account) {
        if (account == null) {
            return null;
        }

        return new AccountEntity(
                account.getId(),
                account.getNumber(),
                account.getAvailableBalance(),
                account.getRetainedBalance(),
                account.getStatus(),
                account.getUser() != null ? UserEntityMapper.mapToEntity(account.getUser()) : null);

    }

    public static Account mapToModel(AccountEntity accountEntity) {
        if (accountEntity == null) {
            return null;
        }

        return new Account(
                accountEntity.getId(),
                accountEntity.getNumber(),
                accountEntity.getAvailableBalance(),
                accountEntity.getRetainedBalance(),
                accountEntity.getStatus(),
                accountEntity.getUser() != null ? UserEntityMapper.mapToModel(accountEntity.getUser()) : null
        );
    }

}