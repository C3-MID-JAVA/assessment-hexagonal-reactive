package ec.com.sofka.cases.transcationType;

import ec.com.sofka.TransactionType;
import ec.com.sofka.gateway.TransactionTypeRepository;
import reactor.core.publisher.Mono;

public class FindTransactionTypeByIdUseCase {
    private final TransactionTypeRepository transactionTypeRepository;

    public FindTransactionTypeByIdUseCase(TransactionTypeRepository transactionTypeRepository) {
        this.transactionTypeRepository = transactionTypeRepository;
    }

    public Mono<TransactionType> apply(String id) {
        return transactionTypeRepository.findById(id);
    }

}