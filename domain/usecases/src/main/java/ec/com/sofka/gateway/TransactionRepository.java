package ec.com.sofka.gateway;

import ec.com.sofka.Transaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionRepository {
    Mono<Transaction> create(Transaction transaction);
    Flux<Transaction> getAllByAccountId(String accountId);
}
