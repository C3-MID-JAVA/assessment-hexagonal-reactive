package ec.com.sofka.mapper;

import ec.com.sofka.Account;
import ec.com.sofka.document.AccountEntity;

public class AccountRepoMapper {

    public static Account toDomain(AccountEntity entity) {
        if (entity == null) {
            return null;
        }

        Account account = new Account();
        account.setId(entity.getId());
        account.setAccountNumber(entity.getAccountNumber());
        account.setBalance(entity.getBalance());
        account.setCardId(entity.getCardId());
        account.setCustumerId(entity.getCustumerId());
        return account;
    }

    public static AccountEntity toEntity(Account domain) {
        if (domain == null) {
            return null;
        }

        AccountEntity entity = new AccountEntity();
        entity.setId(domain.getId());
        entity.setAccountNumber(domain.getAccountNumber());
        entity.setBalance(domain.getBalance());
        entity.setCardId(domain.getCardId());
        entity.setCustumerId(domain.getCustumerId());
        return entity;
    }
}
