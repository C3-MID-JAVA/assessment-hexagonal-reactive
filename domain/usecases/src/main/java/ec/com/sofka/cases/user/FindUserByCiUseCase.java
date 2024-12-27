package ec.com.sofka.cases.user;

import ec.com.sofka.User;
import ec.com.sofka.gateway.UserRepository;
import reactor.core.publisher.Mono;

public class FindUserByCiUseCase {
    private final UserRepository userRepository;

    public FindUserByCiUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<User> apply(String ci) {
        return userRepository.findByCi(ci);
    }

}