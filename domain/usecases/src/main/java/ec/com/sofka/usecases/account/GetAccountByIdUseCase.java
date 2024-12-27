package ec.com.sofka.usecases.account;

import ec.com.sofka.Account;
import ec.com.sofka.gateway.AccountRepository;
import reactor.core.publisher.Mono;

public class GetAccountByIdUseCase {
    private final AccountRepository accountRepository;

    public GetAccountByIdUseCase(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Mono<Account> apply(String id) {
        return accountRepository.findByAccountById(id)
                .switchIfEmpty(Mono.empty());


    }
}
