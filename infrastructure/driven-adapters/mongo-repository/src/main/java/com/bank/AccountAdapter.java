package com.bank;

import com.bank.config.IAccountMongoRepository;
import com.bank.data.AccountEntity;
import com.bank.gateway.IAccountRepository;
import com.bank.mapper.AccountEntityMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class AccountAdapter implements IAccountRepository {

    private final IAccountMongoRepository repository;

    public AccountAdapter(IAccountMongoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Flux<Account> findAllAccounts() {
        return repository.findAllAccounts().map(AccountEntityMapper::toAccount);
    }

    @Override
    public Mono<Account> findAccountById(String id) {
        return repository.findAccountById(id).map(AccountEntityMapper::toAccount);
    }

    @Override
    public Mono<Account> createAccount(Account account) {
        AccountEntity accEntity = AccountEntityMapper.toAccountEntity(account);
        return repository.createAccount(accEntity).map(AccountEntityMapper::toAccount);
    }
}
