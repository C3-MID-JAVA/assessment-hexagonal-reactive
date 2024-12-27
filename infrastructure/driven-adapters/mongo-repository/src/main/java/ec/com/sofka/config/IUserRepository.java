package ec.com.sofka.config;

import ec.com.sofka.data.UserEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IUserRepository extends ReactiveMongoRepository<UserEntity, String> {

    Mono<UserEntity> save(UserEntity entity);

    Flux<UserEntity> findAll();

    Mono<UserEntity> findByCi(String ci);

    Mono<UserEntity> findById(String id);
}