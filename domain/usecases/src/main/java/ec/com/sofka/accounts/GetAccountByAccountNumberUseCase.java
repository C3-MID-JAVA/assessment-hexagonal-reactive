package ec.com.sofka.accounts;

import ec.com.sofka.Account;
import ec.com.sofka.accounts.gateway.IAccountRepository;
import reactor.core.publisher.Mono;

public class GetAccountByAccountNumberUseCase {

    private final IAccountRepository repository;

    public GetAccountByAccountNumberUseCase(IAccountRepository repository) {
        this.repository = repository;
    }

    public Mono<Account> apply(String accountNumber) {
        return repository.findByAccountNumber(accountNumber).switchIfEmpty(Mono.empty());
    }
}
