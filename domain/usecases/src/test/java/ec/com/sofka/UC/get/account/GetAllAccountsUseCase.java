package ec.com.sofka.UC.get.account;

import ec.com.sofka.Account;
import ec.com.sofka.gateway.AccountRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class GetAllAccountsUseCase {
    private final AccountRepository repository;

    public GetAllAccountsUseCase(AccountRepository repository) {
        this.repository = repository;
    }

    public Flux<Account> apply() {
        return repository.findAll();
    }
}
