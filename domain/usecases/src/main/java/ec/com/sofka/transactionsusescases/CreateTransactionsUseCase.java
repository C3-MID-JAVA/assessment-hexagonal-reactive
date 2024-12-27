package ec.com.sofka.transactionsusescases;

import ec.com.sofka.Transaction;
import ec.com.sofka.gateway.TransactionRepository;
import reactor.core.publisher.Mono;

public class CreateTransactionsUseCase {

    private final TransactionRepository transactionRepository;

    public CreateTransactionsUseCase(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Mono<Transaction> apply(Transaction transaction) {
        return transactionRepository.createTransaction(transaction);
    }

}
