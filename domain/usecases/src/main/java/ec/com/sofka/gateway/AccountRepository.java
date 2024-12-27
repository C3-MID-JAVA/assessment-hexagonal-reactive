package ec.com.sofka.gateway;

import ec.com.sofka.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountRepository {
    Mono<Account> findByAccountNumber(String accountNumber);
   // Mono<Boolean> existsByAccountNumber(String accountNumber);
    Mono<Account> save(Account account);
    Flux<Account> findAll();
   // Mono<Boolean> isAccountNumberUnique(String accountNumber);
}
