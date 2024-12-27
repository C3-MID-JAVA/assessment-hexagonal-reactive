package ec.com.sofka.UC.create;

import ec.com.sofka.Transaction;
import ec.com.sofka.gateway.TransactionRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CreateTransactionUseCase {

    private final TransactionRepository repository;

    public CreateTransactionUseCase(TransactionRepository repository) {
        this.repository = repository;
    }

    public Mono<Transaction> apply(Mono<Transaction> transaction) {
        return repository.createTransaction(transaction);
    }
}
