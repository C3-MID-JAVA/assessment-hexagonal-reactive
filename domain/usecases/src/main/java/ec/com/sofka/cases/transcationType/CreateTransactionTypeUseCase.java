package ec.com.sofka.cases.transcationType;

import ec.com.sofka.TransactionType;
import ec.com.sofka.exception.ConflictException;
import ec.com.sofka.gateway.TransactionTypeRepository;
import reactor.core.publisher.Mono;

public class CreateTransactionTypeUseCase {
    private final TransactionTypeRepository transactionTypeRepository;

    public CreateTransactionTypeUseCase(TransactionTypeRepository transactionTypeRepository) {
        this.transactionTypeRepository = transactionTypeRepository;
    }

    public Mono<TransactionType> apply(TransactionType transactionType) {
        return transactionTypeRepository.findByType(transactionType.getType())
                .flatMap(userFound -> Mono.<TransactionType>error(new ConflictException("The transaction type is already registered.")))
                .switchIfEmpty(transactionTypeRepository.save(transactionType));
    }

}