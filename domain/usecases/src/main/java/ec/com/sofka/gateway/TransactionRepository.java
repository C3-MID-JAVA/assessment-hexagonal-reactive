package ec.com.sofka.gateway;

import ec.com.sofka.Transaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface TransactionRepository {
    Flux<Transaction> findAll();
    Mono<Transaction> save(Transaction transaction);
    Flux<Transaction> findByAccountNumber(String accountNumber);
}
