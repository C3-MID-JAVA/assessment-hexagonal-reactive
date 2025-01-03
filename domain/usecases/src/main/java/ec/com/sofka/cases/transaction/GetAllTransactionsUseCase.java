package ec.com.sofka.cases.transaction;

import ec.com.sofka.Transaction;
import ec.com.sofka.gateway.ITransactionRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class GetAllTransactionsUseCase {
    private final ITransactionRepository transactionRepository;

    public GetAllTransactionsUseCase(ITransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Flux<Transaction> getAll() {
        return transactionRepository.getAllTransactions();
    }
}