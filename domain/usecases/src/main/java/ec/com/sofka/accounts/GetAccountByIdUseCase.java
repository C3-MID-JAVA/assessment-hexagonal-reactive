package ec.com.sofka.accounts;

import ec.com.sofka.Account;
import ec.com.sofka.gateway.IAccountRepository;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;

public class GetAccountByIdUseCase {

    private final IAccountRepository repository;

    public GetAccountByIdUseCase(IAccountRepository repository) {
        this.repository = repository;
    }

    public Mono<Account> apply(String id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new NoSuchElementException("No account found with id: " + id)));

    }

}
