package ec.com.sofka.gateway;

import ec.com.sofka.Account;
import ec.com.sofka.Transaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionRepository {
    Flux<Transaction> getAllTransactions();
    Mono<Transaction> createTransaction(Transaction account);
}
