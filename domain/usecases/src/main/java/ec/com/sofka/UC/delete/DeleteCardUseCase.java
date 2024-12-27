package ec.com.sofka.UC.delete;

import ec.com.sofka.gateway.CardRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class DeleteCardUseCase {
    private final CardRepository repository;

    public DeleteCardUseCase(CardRepository repository) {
        this.repository = repository;
    }

    public Mono<Void> apply(int id) {
        return null;
    }
}
