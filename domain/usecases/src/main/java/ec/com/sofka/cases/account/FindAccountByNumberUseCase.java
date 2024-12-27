package ec.com.sofka.cases.account;

import ec.com.sofka.Account;
import ec.com.sofka.gateway.AccountRepository;
import reactor.core.publisher.Mono;

public class FindAccountByNumberUseCase {
    private final AccountRepository accountRepository;

    public FindAccountByNumberUseCase(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Mono<Account> apply(String number) {
        return accountRepository.findByNumber(number);
    }

}