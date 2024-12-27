package ec.com.sofka.usecases.transaction;

import ec.com.sofka.Transaction;
import ec.com.sofka.gateway.TransactionRepository;
import reactor.core.publisher.Mono;

public class GetTransactionByIdUseCase {
    private final TransactionRepository transactionRepository;

    public GetTransactionByIdUseCase(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Mono<Transaction> apply(String id) {
        return transactionRepository.findTransactionById(id)
                .switchIfEmpty(Mono.empty());
    }
}