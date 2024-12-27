package ec.com.sofka.cases.user;

import ec.com.sofka.User;
import ec.com.sofka.gateway.UserRepository;
import reactor.core.publisher.Mono;

public class FindUserByIdUseCase {
    private final UserRepository userRepository;

    public FindUserByIdUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<User> apply(String id) {
        return userRepository.findById(id);
    }

}