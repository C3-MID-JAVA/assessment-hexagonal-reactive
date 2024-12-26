package ec.com.sofka.gateway;

import ec.com.sofka.Account;
import ec.com.sofka.Transaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ITransactionRepository {
    Flux<Transaction> findAll();
    Mono<Transaction> save(Account cuenta);
    Mono<Transaction> findByAccountNumber(String id);
    Mono<Transaction> findById(String id);
}
