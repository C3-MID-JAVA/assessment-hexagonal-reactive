package co.com.sofkau.gateway;

import co.com.sofkau.Card;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface ICardRepository {
    Mono<Boolean> existsByCardCVV(String cardCVV);
    Mono<Card> findByCardNumber(String cardNumber);
    Flux<Card> findByAccount_Id(String accountId);
    Mono<Card> save(Card card);
}
