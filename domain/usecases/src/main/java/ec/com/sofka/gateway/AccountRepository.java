package ec.com.sofka.gateway;

import ec.com.sofka.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountRepository {

    Mono<Account> save(Account account);

    Mono<Account> findByNumber(String number);

    Flux<Account> findAll();

}