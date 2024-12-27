package ec.com.sofka.UC.get.card;

import ec.com.sofka.Card;
import ec.com.sofka.gateway.CardRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class GetCardsByTypeUseCase {
    private final CardRepository repository;

    public GetCardsByTypeUseCase(CardRepository repository) {
        this.repository = repository;
    }

    public Flux<Card> apply(String s) {
        return null;
    }
}
