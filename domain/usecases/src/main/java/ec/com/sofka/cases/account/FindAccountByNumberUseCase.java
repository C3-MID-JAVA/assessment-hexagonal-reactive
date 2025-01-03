package ec.com.sofka.cases.account;

import ec.com.sofka.Account;
import ec.com.sofka.gateway.IAccountRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class FindAccountByNumberUseCase {
    private final IAccountRepository accountRepository;

    public FindAccountByNumberUseCase(IAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Mono<Account> find(String number) {
        return accountRepository.findAccountByNumber(number);
    }
}