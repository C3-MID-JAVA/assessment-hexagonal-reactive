package ec.com.sofka.gateway;

import ec.com.sofka.Transaction;
import reactor.core.publisher.Mono;

public interface TransactionRepository {
    Mono<Transaction> findTransactionById(String id);
    Mono<Transaction> saveTransaction(Transaction transaction);
}