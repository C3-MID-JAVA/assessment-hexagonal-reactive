package co.com.sofka.bankingaccount.bankingaccount.infrastructure.persistence.mapper;

import co.com.sofka.bankingaccount.bankingaccount.domain.model.Account;
import co.com.sofka.bankingaccount.bankingaccount.infrastructure.drivenAdapters.mongoRepository.entities.AccountEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "entitledUser", target = "entitledUser")
    @Mapping(source = "balance", target = "balance")
    AccountEntity toAccountEntity(Account account);
    List<AccountEntity> toAccountEntityList(List<Account> accounts);
    @InheritInverseConfiguration
    Account toAccount(AccountEntity accountEntity);
}
