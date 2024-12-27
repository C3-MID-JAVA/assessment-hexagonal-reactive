package ec.com.sofka.cases.transcationType;

import ec.com.sofka.TransactionType;
import ec.com.sofka.gateway.TransactionTypeRepository;
import reactor.core.publisher.Flux;

public class GetAllTransactionTypesUseCase {
    private final TransactionTypeRepository transactionTypeRepository;

    public GetAllTransactionTypesUseCase(TransactionTypeRepository transactionTypeRepository) {
        this.transactionTypeRepository = transactionTypeRepository;
    }

    public Flux<TransactionType> apply() {
        return transactionTypeRepository.findAll();
    }

}