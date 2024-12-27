package co.com.sofkau.handlers;

import co.com.sofkau.CreateCardUseCase;
import co.com.sofkau.GetCardsByAccountUseCase;
import co.com.sofkau.data.CardDTO;
import co.com.sofkau.mapper.CardDTOMapper;
import co.com.sofkau.mapper.CardMapper;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class CardHandler {
    private final CreateCardUseCase createCardUseCase;
    private final GetCardsByAccountUseCase getCardsByAccountUseCase;


    public CardHandler(CreateCardUseCase createCardUseCase, GetCardsByAccountUseCase getCardsByAccountUseCase) {
        this.createCardUseCase = createCardUseCase;
        this.getCardsByAccountUseCase = getCardsByAccountUseCase;
    }

    public Flux<CardDTO> getCardsByAccountNumber(String accountNumber) {
        return getCardsByAccountUseCase.apply(accountNumber).map(CardDTOMapper::toCardDTO);
    }



    public Mono<CardDTO> createCard(CardDTO cardDTO) {
        return createCardUseCase.apply(CardDTOMapper.toCard(cardDTO)).map(CardDTOMapper::toCardDTO);
    }
}
