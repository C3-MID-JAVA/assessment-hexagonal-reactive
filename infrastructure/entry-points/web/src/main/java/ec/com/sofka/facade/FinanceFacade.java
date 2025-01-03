package ec.com.sofka.facade;

import ec.com.sofka.cases.transaction.CreateTransactionUseCase;
import ec.com.sofka.cases.transaction.GetAllTransactionsUseCase;
import ec.com.sofka.dto.response.TransactionResponseDTO;
import ec.com.sofka.dto.request.TransactionRequestDTO;
import ec.com.sofka.mapper.TransactionRequestMapper;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class FinanceFacade {

    private final CreateTransactionUseCase createTransactionUseCase;
    private final GetAllTransactionsUseCase getAllTransactionsUseCase;

    public FinanceFacade(CreateTransactionUseCase createTransactionUseCase, GetAllTransactionsUseCase getAllTransactionsUseCase) {
        this.createTransactionUseCase = createTransactionUseCase;
        this.getAllTransactionsUseCase = getAllTransactionsUseCase;
    }

    public Mono<TransactionResponseDTO> processTransaction(TransactionRequestDTO request) {
        return createTransactionUseCase.create(TransactionRequestMapper.mapToModel(request))
                .map(TransactionRequestMapper::mapToDTO);
    }

    public Flux<TransactionResponseDTO> getAllTransactions() {
        return getAllTransactionsUseCase.getAll()
                .map(TransactionRequestMapper::mapToDTO);
    }
}
