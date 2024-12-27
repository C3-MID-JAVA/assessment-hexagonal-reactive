package ec.com.sofka.gateway;

import ec.com.sofka.Transaction;
import reactor.core.publisher.Mono;

public interface TransactionRepositoryGateway {

    Mono<Transaction> save(Transaction transaction);
}
