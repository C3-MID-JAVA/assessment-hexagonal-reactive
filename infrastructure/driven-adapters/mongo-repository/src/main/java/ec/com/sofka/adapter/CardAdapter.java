package ec.com.sofka.adapter;

import ec.com.sofka.Card;
import ec.com.sofka.data.CardDocument;
import ec.com.sofka.gateway.CardRepository;
import ec.com.sofka.mapper.DocumentToModelMapper;
import ec.com.sofka.mapper.ModelToDocumentMapper;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class CardAdapter implements CardRepository {
    private final ReactiveMongoTemplate reactiveMongoTemplate;

    public CardAdapter(ReactiveMongoTemplate reactiveMongoTemplate) {
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }

    @Override
    public Mono<Card> createCard(Mono<Card> card) {
        return card.flatMap(cardModel -> ModelToDocumentMapper.toCard.apply(Mono.just(cardModel)))
                .flatMap(reactiveMongoTemplate::save)
                .flatMap(cardDocument -> DocumentToModelMapper.toCard.apply(Mono.just(cardDocument)));
    }

    @Override
    public Mono<Card> findCardById(Mono<Integer> id) {
        return id.flatMap(cardId -> reactiveMongoTemplate.findById(cardId, CardDocument.class))
                .flatMap(cardDocument -> DocumentToModelMapper.toCard.apply(Mono.just(cardDocument)));
    }

    @Override
    public Mono<Boolean> existsById(Mono<Integer> id) {
        return id.flatMap(cardId -> reactiveMongoTemplate
                .exists(Query.query(Criteria.where("_id").is(cardId)), CardDocument.class));
    }

    @Override
    public Mono<Card> findCardByCardNumber(Mono<String> cardNumber) {
        return cardNumber.flatMap(number -> reactiveMongoTemplate
                        .findOne(Query.query(Criteria.where("cardNumber").is(number)), CardDocument.class))
                .flatMap(cardDocument -> DocumentToModelMapper.toCard.apply(Mono.just(cardDocument)));
    }

    @Override
    public Flux<Card> findAll() {
        return reactiveMongoTemplate.findAll(CardDocument.class)
                .flatMap(cardDocument -> DocumentToModelMapper.toCard.apply(Mono.just(cardDocument)));
    }

    @Override
    public Flux<Card> findCardsByCardType(Mono<String> cardType) {
        return cardType.flatMapMany(type ->
                        reactiveMongoTemplate.find(Query.query(Criteria.where("cardType").is(type)),
                                CardDocument.class))
                .flatMap(cardDocument -> DocumentToModelMapper.toCard.apply(Mono.just(cardDocument)));
    }

    @Override
    public Mono<Void> deleteById(Mono<Integer> id) {
        return id.flatMap(cardId -> reactiveMongoTemplate
                        .remove(Query.query(Criteria.where("_id").is(cardId)), CardDocument.class))
                .then();
    }
}
