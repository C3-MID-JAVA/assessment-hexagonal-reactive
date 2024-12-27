package com.bank.account;

import com.bank.Account;
import com.bank.gateway.IAccountRepository;
import reactor.core.publisher.Mono;

public class FindAccountByIdUseCase {
    private final IAccountRepository repository;

    public FindAccountByIdUseCase(IAccountRepository repository) {
        this.repository = repository;
    }

    public Mono<Account> apply(String id) {
        return repository.findAccountById(id);
    }
}
