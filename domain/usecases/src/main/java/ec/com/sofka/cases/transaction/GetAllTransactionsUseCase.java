package ec.com.sofka.cases.transaction;

import ec.com.sofka.Transaction;
import ec.com.sofka.gateway.TransactionRepository;
import reactor.core.publisher.Flux;

public class GetAllTransactionsUseCase {
    private final TransactionRepository transactionRepository;

    public GetAllTransactionsUseCase(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Flux<Transaction> apply() {
        return transactionRepository.findAll();
    }

}