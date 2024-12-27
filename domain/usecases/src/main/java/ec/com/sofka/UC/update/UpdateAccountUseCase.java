package ec.com.sofka.UC.update;

import ec.com.sofka.Account;
import ec.com.sofka.gateway.AccountRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class UpdateAccountUseCase {
    private final AccountRepository repository;

    public UpdateAccountUseCase(AccountRepository repository) {
        this.repository = repository;
    }

    public Mono<Account> apply(Mono<Account> account) {
        return repository.updateAccount(account);
    }
}
