package ec.com.sofka.accounts.gateway;

import ec.com.sofka.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IAccountRepository {
    Mono<Account> findByAccountNumber(String id);
    Mono<Account> save(Account cuenta);
    Flux<Account> findAll();
    Mono<Account> findById(String id);

}
