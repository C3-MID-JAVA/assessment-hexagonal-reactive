package ec.com.sofka.UC.get.transaction;

import ec.com.sofka.Transaction;
import ec.com.sofka.gateway.TransactionRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@Component
public class GetTransactionsByDateUseCase {
    private final TransactionRepository repository;

    public GetTransactionsByDateUseCase(TransactionRepository repository) {
        this.repository = repository;
    }

    public Flux<Transaction> apply(LocalDateTime date) {
        return null;
    }
}
