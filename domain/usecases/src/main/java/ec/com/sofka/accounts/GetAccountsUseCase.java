package ec.com.sofka.accounts;

import ec.com.sofka.Account;
import ec.com.sofka.gateway.IAccountRepository;
import reactor.core.publisher.Flux;

public class GetAccountsUseCase {

    private final IAccountRepository repository;

    public GetAccountsUseCase(IAccountRepository repository) {
        this.repository = repository;
    }

    public Flux<Account> apply() {
        return repository.findAll();
    }

}
