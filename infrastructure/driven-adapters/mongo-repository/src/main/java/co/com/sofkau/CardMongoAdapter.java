package co.com.sofkau;

import co.com.sofkau.config.ICardMongoRepository;
import co.com.sofkau.gateway.ICardRepository;
import co.com.sofkau.mapper.CardMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class CardMongoAdapter implements ICardRepository {

    private final ICardMongoRepository cardMongoRepository;

    public CardMongoAdapter(ICardMongoRepository cardMongoRepository) {
        this.cardMongoRepository = cardMongoRepository;
    }

    @Override
    public Mono<Boolean> existsByCardCVV(String cardCVV) {
        return cardMongoRepository.existsByCardCVV(cardCVV);
    }

    @Override
    public Mono<Card> findByCardNumber(String cardNumber) {
        return cardMongoRepository.findByCardNumber(cardNumber).map(CardMapper::toCard);
    }

    @Override
    public Flux<Card> findByAccount_Id(String accountId) {
        return cardMongoRepository.findByAccount_Id(accountId).map(CardMapper::toCard);
    }

    @Override
    public Mono<Card> save(Card card) {
        return cardMongoRepository.save(CardMapper.toCardEntity(card)).map(CardMapper::toCard);
    }
}
