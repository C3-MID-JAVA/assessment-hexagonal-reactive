package ec.com.sofka.handler.transaction;

import ec.com.sofka.Transaction;
import ec.com.sofka.cases.transaction.CreateTransactionUseCase;
import ec.com.sofka.cases.transcationType.FindTransactionTypeByIdUseCase;
import ec.com.sofka.data.TransactionRequestDTO;
import ec.com.sofka.data.TransactionResponseDTO;
import ec.com.sofka.mapper.TransactionModelMapper;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CreateTransactionHandler {
    private final CreateTransactionUseCase createTransactionUseCase;
    private final FindTransactionTypeByIdUseCase findTransactionTypeByIdUseCase;

    public CreateTransactionHandler(CreateTransactionUseCase createTransactionUseCase, FindTransactionTypeByIdUseCase findTransactionTypeByIdUseCase) {
        this.createTransactionUseCase = createTransactionUseCase;
        this.findTransactionTypeByIdUseCase = findTransactionTypeByIdUseCase;
    }

    public Mono<TransactionResponseDTO> save(TransactionRequestDTO transactionRequestDTO) {
        return findTransactionTypeByIdUseCase.apply(transactionRequestDTO.getTransactionTypeId())
                .flatMap(transactionType -> {
                    Transaction transaction = TransactionModelMapper.mapToModel(transactionRequestDTO);
                    transaction.setTransactionType(transactionType);
                    return createTransactionUseCase.apply(transaction);
                })
                .map(TransactionModelMapper::mapToDTO);

    }

}