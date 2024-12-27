package ec.com.sofka.mapper;

import ec.com.sofka.Account;
import ec.com.sofka.document.AccountEnti;

public class AccountRepoMapper {

    public static Account toDomain(AccountEnti entity) {
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

    public static AccountEnti toEntity(Account domain) {
        if (domain == null) {
            return null;
        }

        AccountEnti entity = new AccountEnti();
        entity.setId(domain.getId());
        entity.setAccountNumber(domain.getAccountNumber());
        entity.setBalance(domain.getBalance());
        entity.setCardId(domain.getCardId());
        entity.setCustumerId(domain.getCustumerId());
        return entity;
    }
}
