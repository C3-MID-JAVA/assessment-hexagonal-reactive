package ec.com.sofka.rules;

import ec.com.sofka.Transaction;
import reactor.core.publisher.Mono;

@FunctionalInterface
public interface ValidateTransaction {
    Mono<Transaction> validateTransaction(Transaction transaction);
}
