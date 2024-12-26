package ec.com.sofka.gateway;

import ec.com.sofka.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountRepository {
    Mono<Account> create(Account account);
    Flux<Account> getAllByUserId(String userId);
    Mono<Account> findByAccountNumber(String accountNumber);
    Mono<Account> findById(String id);
}
