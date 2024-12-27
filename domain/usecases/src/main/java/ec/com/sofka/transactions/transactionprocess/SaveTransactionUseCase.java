package ec.com.sofka.transactions.transactionprocess;

import ec.com.sofka.Transaction;
import ec.com.sofka.gateway.ITransactionRepository;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public class SaveTransactionUseCase {

    private final ITransactionRepository repository;

    public SaveTransactionUseCase(ITransactionRepository repository) {
        this.repository = repository;
    }

    public Mono<Transaction> apply(Transaction transaction) {
        transaction.setDate(LocalDateTime.now());
        return repository.save(transaction);
    }
}
