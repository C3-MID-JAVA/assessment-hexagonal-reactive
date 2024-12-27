package ec.com.sofka.cases.user;

import ec.com.sofka.User;
import ec.com.sofka.gateway.UserRepository;
import reactor.core.publisher.Flux;

public class GetAllUsersUseCase {
    private final UserRepository userRepository;

    public GetAllUsersUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public  Flux<User> apply(){
        return userRepository.findAll();
    }

}