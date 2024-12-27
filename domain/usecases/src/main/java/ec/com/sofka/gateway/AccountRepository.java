package ec.com.sofka.gateway;

import ec.com.sofka.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountRepository {

    Account findByAcccountId(String id);

    Mono<Account> save(Account account);
    Mono<Account> findByAccountNumber(String accountNumber);
    Flux<Account> findAll();
    Mono<Void> deleteByAccountNumber(String accountNumber);

}
