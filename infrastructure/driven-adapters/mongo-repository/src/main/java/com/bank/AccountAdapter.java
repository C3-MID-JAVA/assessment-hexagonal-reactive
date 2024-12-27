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
        return repository.findAll().map(AccountEntityMapper::toAccount);
    }

    @Override
    public Mono<Account> findAccountById(String id) {
        return repository.findById(id).map(AccountEntityMapper::toAccount);
    }

    @Override
    public Mono<Account> createAccount(Account account) {
        AccountEntity accEntity = AccountEntityMapper.toAccountEntity(account);
        return repository.insert(accEntity).map(AccountEntityMapper::toAccount);
    }
}
