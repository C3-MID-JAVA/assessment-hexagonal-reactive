package ec.com.sofka.accounts;

import ec.com.sofka.Account;
import ec.com.sofka.gateway.IAccountRepository;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;

public class GetAccountByAccountNumberUseCase {

    private final IAccountRepository repository;

    public GetAccountByAccountNumberUseCase(IAccountRepository repository) {
        this.repository = repository;
    }

    public Mono<Account> apply(String accountNumber) {
        return repository.findByAccountNumber(accountNumber).switchIfEmpty(Mono.error(new NoSuchElementException("The account with the provided account number does not exist")));
    }
}