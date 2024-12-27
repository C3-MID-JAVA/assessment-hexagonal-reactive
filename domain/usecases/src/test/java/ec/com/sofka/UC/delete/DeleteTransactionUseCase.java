package ec.com.sofka.UC.delete;

import ec.com.sofka.gateway.TransactionRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class DeleteTransactionUseCase {
    private final TransactionRepository repository;

    public DeleteTransactionUseCase(TransactionRepository repository) {
        this.repository = repository;
    }

    public Mono<Void> apply(int id) {
        return null;
    }
}
