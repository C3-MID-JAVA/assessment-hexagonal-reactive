package ec.com.sofka.config;

import ec.com.sofka.User;
import ec.com.sofka.data.UserEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UserMongoRepository extends ReactiveMongoRepository<UserEntity, String> {
    Mono<UserEntity> findByDocumentId(String documentId);
}
