package ec.com.sofka.account;

import ec.com.sofka.Account;
import ec.com.sofka.exception.NotFoundException;
import ec.com.sofka.gateway.AccountRepository;
import ec.com.sofka.gateway.UserRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class GetAllByUserIdUseCase {

    private final AccountRepository repository;
    private final UserRepository userRepository;

    public GetAllByUserIdUseCase(AccountRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public Flux<Account> apply(String userid){
        return userRepository.findById(userid)
                .switchIfEmpty(Mono.error(new NotFoundException("User not found")))
                .flatMapMany(user -> {
                    return repository.getAllByUserId(userid);
                });
    }
}
