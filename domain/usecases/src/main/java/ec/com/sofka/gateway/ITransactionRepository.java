package ec.com.sofka.gateway;

import ec.com.sofka.Transaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ITransactionRepository {
    Mono<Transaction> createTransaction(Transaction transaction);
    Flux<Transaction> getAllTransactions();
}