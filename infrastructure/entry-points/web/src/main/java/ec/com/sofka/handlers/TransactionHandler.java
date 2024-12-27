package ec.com.sofka.handlers;

import ec.com.sofka.GetTransactionsByAccountUseCase;
import ec.com.sofka.RegisterTransactionUseCase;
import ec.com.sofka.data.TransactionRequestDTO;
import ec.com.sofka.data.TransactionResponseDTO;
import ec.com.sofka.mapper.TransactionDTOMapper;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class TransactionHandler {

    private final RegisterTransactionUseCase registerTransactionUseCase;
    private final GetTransactionsByAccountUseCase getTransactionsByAccountUseCase;

    public TransactionHandler(RegisterTransactionUseCase registerTransactionUseCase,
                              GetTransactionsByAccountUseCase getTransactionsByAccountUseCase) {
        this.registerTransactionUseCase = registerTransactionUseCase;
        this.getTransactionsByAccountUseCase = getTransactionsByAccountUseCase;
    }

    public Mono<TransactionResponseDTO> registerTransaction(TransactionRequestDTO transactionRequestDTO) {
        return registerTransactionUseCase.apply(transactionRequestDTO.getAccountNumber(),TransactionDTOMapper.toEntity(transactionRequestDTO))
                .map(TransactionDTOMapper::fromEntity);
    }


    public Flux<TransactionResponseDTO> getTransactionsByAccount(String accountNumber) {
        return getTransactionsByAccountUseCase.apply(accountNumber)
                .map(TransactionDTOMapper::fromEntity);
    }
}