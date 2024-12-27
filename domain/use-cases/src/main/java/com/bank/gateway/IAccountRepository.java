package com.bank.gateway;

import com.bank.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IAccountRepository {
    Flux<Account> findAllAccounts();

    Mono<Account> findAccountById(String id);

    Mono<Account> createAccount(Account account);
}