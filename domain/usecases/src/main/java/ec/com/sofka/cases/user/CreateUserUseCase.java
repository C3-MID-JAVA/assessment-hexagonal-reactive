package ec.com.sofka.cases.user;

import ec.com.sofka.exception.ConflictException;
import ec.com.sofka.User;
import ec.com.sofka.gateway.UserRepository;
import reactor.core.publisher.Mono;

public class CreateUserUseCase {
    private final UserRepository userRepository;

    public CreateUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<User> apply(User user) {
        return userRepository.findByCi(user.getCi())
                .flatMap(userFound -> Mono.<User>error(new ConflictException("The user is already registered.")))
                .switchIfEmpty(userRepository.save(user));
    }

}