package ec.com.sofka.user;

import ec.com.sofka.User;
import ec.com.sofka.gateway.UserRepository;
import reactor.core.publisher.Flux;

public class GetAllUseCase {

    private final UserRepository repository;

    public GetAllUseCase(UserRepository repository) {
        this.repository = repository;
    }

    public Flux<User> apply(){
        return repository.getAll();
    }
}
