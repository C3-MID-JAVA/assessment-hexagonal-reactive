package com.bank.account;

import com.bank.Account;
import com.bank.gateway.IAccountRepository;
import reactor.core.publisher.Flux;

public class FindAllAccountsUseCase {
    private final IAccountRepository repository;

    public FindAllAccountsUseCase(IAccountRepository repository) {
        this.repository = repository;
    }

    public Flux<Account> apply() {
        return repository.findAllAccounts();
    }
}
