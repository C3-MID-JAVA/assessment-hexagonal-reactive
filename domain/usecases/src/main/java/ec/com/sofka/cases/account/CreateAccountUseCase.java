package ec.com.sofka.cases.account;

import ec.com.sofka.Account;
import ec.com.sofka.exception.ConflictException;
import ec.com.sofka.gateway.AccountRepository;
import reactor.core.publisher.Mono;

public class CreateAccountUseCase {
    private final AccountRepository accountRepository;

    public CreateAccountUseCase(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Mono<Account> apply(Account account) {
        return accountRepository.findByNumber(account.getNumber())
                .flatMap(accountFound -> Mono.<Account>error(new ConflictException("The account is already registered.")))
                .switchIfEmpty(accountRepository.save(account));
    }

}