package ec.com.sofka.repository;

import ec.com.sofka.document.CardEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICardRepository extends ReactiveMongoRepository<CardEntity, String> {
}
