package co.com.sofkau;

import co.com.sofkau.gateway.ICardRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Component
public class GetCardByCardNumberUseCase {
    private final ICardRepository cardRepository;

    public GetCardByCardNumberUseCase(ICardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public Mono<Card> apply(String cardNumber) {
        return cardRepository.findByCardNumber(cardNumber);

    }
}
