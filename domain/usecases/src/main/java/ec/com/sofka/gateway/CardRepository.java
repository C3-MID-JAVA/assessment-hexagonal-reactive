package ec.com.sofka.gateway;

import ec.com.sofka.Card;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CardRepository {

    Mono<Card> createCard(Mono<Card> card);

    Mono<Card> findCardById(Mono<Integer> id);

    Mono<Boolean> existsById(Mono<Integer> id);

    Mono<Card> findCardByCardNumber(Mono<String> card_number);

    Flux<Card> findAll();

    Flux<Card> findCardsByCardType(Mono<String> card_type);

    Mono<Void> deleteById(Mono<Integer> id);
}
