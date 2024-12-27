package ec.com.sofka.gateway;

import ec.com.sofka.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository {

    Mono<User> save(User user);

    Flux<User> findAll();

    Mono<User> findById(String id);

    Mono<User> findByCi(String ci);

}