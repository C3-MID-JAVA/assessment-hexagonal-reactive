package ec.com.sofka.gateway;

import ec.com.sofka.TransactionType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionTypeRepository {

    Mono<TransactionType> save(TransactionType transactionType);

    Flux<TransactionType> findAll();

    Mono<TransactionType> findById(String transactionTypeId);

    Mono<TransactionType> findByType(String transactionTypeId);

}