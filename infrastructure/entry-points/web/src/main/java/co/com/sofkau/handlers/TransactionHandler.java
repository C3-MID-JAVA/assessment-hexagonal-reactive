package co.com.sofkau.handlers;

import co.com.sofkau.CreateTransactionUseCase;
import co.com.sofkau.data.TransactionDTO;
import co.com.sofkau.mapper.TransactionDTOMapper;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class TransactionHandler {

    private final CreateTransactionUseCase createTransactionUseCase;


    public TransactionHandler(CreateTransactionUseCase createTransactionUseCase) {
        this.createTransactionUseCase = createTransactionUseCase;
    }

    public Mono<TransactionDTO> createTransaction(TransactionDTO transactionDTO) {
        return createTransactionUseCase.apply(TransactionDTOMapper.toTransaction(transactionDTO))
                .map(TransactionDTOMapper::toDTO);
    }
}
