package ec.com.sofka.gateway;

import ec.com.sofka.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountRepository {

    Mono<Account> createAccount(Mono<Account> account);

    Mono<Account> findAccountById(Mono<Integer> id);

    Flux<Account> getAccountsByCustomerId(Mono<Integer> customer_id);

    Flux<Account> findAll();

    Mono<Account> updateAccount(Mono<Account> account);

    Mono<Void> deleteById(Mono<Integer> id);
}