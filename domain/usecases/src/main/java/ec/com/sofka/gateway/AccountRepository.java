package ec.com.sofka.gateway;

import ec.com.sofka.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountRepository {
    Mono<Account> findByAccountById(String id);
    Flux<Account> findAllAccounts();
    Mono<Account> saveAccount(Account account);
}
