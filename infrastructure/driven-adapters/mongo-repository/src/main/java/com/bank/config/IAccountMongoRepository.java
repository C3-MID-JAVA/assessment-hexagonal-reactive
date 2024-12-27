package com.bank.config;

import com.bank.data.AccountEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IAccountMongoRepository extends ReactiveMongoRepository<AccountEntity, String> {
    Flux<AccountEntity> findAllAccounts();

    Mono<AccountEntity> findAccountById(String id);

    Mono<AccountEntity> createAccount(AccountEntity account);
}