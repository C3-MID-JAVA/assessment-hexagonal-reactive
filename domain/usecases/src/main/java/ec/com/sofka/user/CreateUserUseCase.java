package ec.com.sofka.user;

import ec.com.sofka.User;
import ec.com.sofka.exception.ConflictException;
import ec.com.sofka.gateway.UserRepository;
import reactor.core.publisher.Mono;

public class CreateUserUseCase {

    private final UserRepository repository;

    public CreateUserUseCase(UserRepository repository) {
        this.repository = repository;
    }

    public Mono<User> apply(User user){
        return repository.findByDocumentId(user.getDocumentId())
                .flatMap(existingUser -> Mono.error(new ConflictException("Document ID already exists.")))
                .then(Mono.defer(() -> repository.create(user)));
    }
}
