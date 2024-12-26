package ec.com.sofka.gateway;

import ec.com.sofka.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository {
    Mono<User> create(User user);
    Flux<User> getAll();
    Mono<User> findByDocumentId(String documentId);
    Mono<User> findById(String id);
}
