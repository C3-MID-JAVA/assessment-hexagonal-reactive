package ec.com.sofka.UC.get.card;

import ec.com.sofka.Card;
import ec.com.sofka.gateway.CardRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class GetCardByNumberUseCase {
    private final CardRepository repository;

    public GetCardByNumberUseCase(CardRepository repository) {
        this.repository = repository;
    }

    public Mono<Card> apply(String apply) {
        return null;
    }
}
