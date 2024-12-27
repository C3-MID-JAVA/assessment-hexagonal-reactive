package com.bank.account;

import com.bank.Account;
import com.bank.gateway.IAccountRepository;
import reactor.core.publisher.Mono;

public class CreateAccountUseCase {
    private final IAccountRepository repository;

    public CreateAccountUseCase(IAccountRepository repository) {
        this.repository = repository;
    }

    public Mono<Account> apply(Account account) {
        return repository.createAccount(account);
    }
}
