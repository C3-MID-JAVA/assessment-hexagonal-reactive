package ec.com.sofka.gateway;

import ec.com.sofka.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface IAccountRepository {
    Mono<Account> findByAccountNumber(String accountNumber);
    Mono<Account> save(Account cuenta);
    Flux<Account> findAll();
    Mono<Account> findById(String id);
}
