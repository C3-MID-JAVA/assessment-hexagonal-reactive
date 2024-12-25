package ec.com.sofka.accounts;

import ec.com.sofka.Account;
import ec.com.sofka.ConflictException;
import ec.com.sofka.accounts.gateway.IAccountRepository;
import reactor.core.publisher.Mono;

public class CreateAccountUseCase {

    private final IAccountRepository repository;
    public CreateAccountUseCase(IAccountRepository repository) {
        this.repository = repository;
    }

    public Mono<Account> apply(Account account) {
        return repository.findByAccountNumber(account.getAccountNumber())
                .flatMap(cuenta -> Mono.<Account>error(new ConflictException("The account number is already registered.")))
                .switchIfEmpty(repository.save(account));
    }
}
