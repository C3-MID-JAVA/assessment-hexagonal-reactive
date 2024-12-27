package ec.com.sofka.account;

import ec.com.sofka.Account;
import ec.com.sofka.exception.NotFoundException;
import ec.com.sofka.gateway.AccountRepository;
import ec.com.sofka.gateway.UserRepository;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.UUID;

public class CreateAccountUseCase {

    private final AccountRepository repository;
    private final UserRepository userRepository;

    public CreateAccountUseCase(AccountRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public Mono<Account> apply(Account account) {
        return userRepository.findById(account.getUserId())
                .switchIfEmpty(Mono.error(new NotFoundException("User not found")))
                .flatMap(user -> {
                            account.setBalance(BigDecimal.valueOf(0.0));
                            account.setAccountNumber(UUID.randomUUID().toString().substring(0, 8));
                            account.setUserId(user.getId());
                            return repository.create(account);
                        }
                );

    }
}
