package ec.com.sofka.accountusescases;

import ec.com.sofka.Account;
import ec.com.sofka.gateway.AccountRepository;
import reactor.core.publisher.Mono;

public class CreateAccountUseCase {

    private final AccountRepository repository;

    public CreateAccountUseCase(AccountRepository repository) {
        this.repository = repository;
    }

    public Mono<Account> apply(Account account){
        return repository.CreateAcccount(account);
    }

}
