package co.com.sofkau;


import co.com.sofkau.gateway.ICardRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Component
public class CreateCardUseCase {
    private final ICardRepository cardRepository;
    private final GetAccountByAccountNumberUseCase getAccountByAccountNumberUseCase;
    private final GetCvvCardUseCase getCvvCardUseCase;
    private final GetCardByCardNumberUseCase getCardByCardNumberUseCase;

    public CreateCardUseCase(ICardRepository cardRepository, GetAccountByAccountNumberUseCase getAccountByAccountNumberUseCase, GetCvvCardUseCase getCvvCardUseCase, GetCardByCardNumberUseCase getCardByCardNumberUseCase) {
        this.cardRepository = cardRepository;
        this.getAccountByAccountNumberUseCase = getAccountByAccountNumberUseCase;
        this.getCvvCardUseCase = getCvvCardUseCase;
        this.getCardByCardNumberUseCase = getCardByCardNumberUseCase;
    }

    public Mono<Card> apply(Card card) {
        return getCardByCardNumberUseCase.apply(card.getCardNumber())
                .hasElement()
                .flatMap(element -> {
                    if (element) {
                        return Mono.error(new RuntimeException("Card already exists"));
                    }
                    return getAccountByAccountNumberUseCase.apply(card.getAccount().getAccountNumber())
                            .flatMap(account -> getCvvCardUseCase.apply()
                                    .flatMap(cvv -> {
                                        card.setCardCVV(cvv);
                                        card.setAccount(account);
                                        return cardRepository.save(card);
                                    }))
                            .switchIfEmpty(Mono.error(new RuntimeException("Account does not exist")));
                });

    }
}
