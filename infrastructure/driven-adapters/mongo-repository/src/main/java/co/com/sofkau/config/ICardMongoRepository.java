package co.com.sofkau.config;

import co.com.sofkau.data.CardEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface ICardMongoRepository extends ReactiveMongoRepository<CardEntity, String> {
    Mono<Boolean> existsByCardCVV(String cardCVV);
    Mono<CardEntity> findByCardNumber(String cardNumber);
    Flux<CardEntity> findByAccount_Id(String accountId);

}
