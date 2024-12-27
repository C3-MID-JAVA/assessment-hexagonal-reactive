package ec.com.sofka.transactionsusescases;

import ec.com.sofka.Transaction;
import ec.com.sofka.gateway.TransactionRepository;
import reactor.core.publisher.Flux;

public class GetAllTransactionsUseCase {

    private final TransactionRepository transactionRepository;

    public GetAllTransactionsUseCase(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Flux<Transaction> getAllTransactions() {
        Flux<Transaction> transactionFlux = transactionRepository.getAllTransactions();
        return transactionRepository.getAllTransactions();
    }

}
