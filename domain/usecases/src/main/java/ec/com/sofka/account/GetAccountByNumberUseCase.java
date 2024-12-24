package ec.com.sofka.account;

import ec.com.sofka.Account;
import ec.com.sofka.exception.NotFoundException;
import ec.com.sofka.gateway.AccountRepository;
import reactor.core.publisher.Mono;

public class GetAccountByNumberUseCase {

    private final AccountRepository repository;

    public GetAccountByNumberUseCase(AccountRepository repository) {
        this.repository = repository;
    }

    public Mono<Account> apply(String accountNumber){
        return repository.findByAccountNumber(accountNumber)
                .switchIfEmpty(Mono.error(new NotFoundException("Account not found")));
    }
}
