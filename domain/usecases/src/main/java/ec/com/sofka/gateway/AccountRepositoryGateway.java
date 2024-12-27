package ec.com.sofka.gateway;

import ec.com.sofka.Account;
import ec.com.sofka.Customer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountRepositoryGateway {

    Mono<Account> save(Account x);
    Mono<Account> findById(String id);
    Flux<Account> findAll();

}
