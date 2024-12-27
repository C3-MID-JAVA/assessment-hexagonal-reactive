package ec.com.sofka.usecases.account;

import ec.com.sofka.Account;
import ec.com.sofka.gateway.AccountRepository;
import reactor.core.publisher.Mono;

public class CreateAccountUseCase {
    private final AccountRepository accountRepository;

    public CreateAccountUseCase(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
    public Mono<Account> apply(Account account) {
        return accountRepository.saveAccount(account)
                .switchIfEmpty(Mono.error(new RuntimeException("Cuenta no fue creada")));
    }
}
