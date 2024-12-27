package ec.com.sofka;

import ec.com.sofka.gateway.AccountRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Component
public class CreateAccountUseCase {
    private final AccountRepository accountRepository;
    private  final GetAccountByAccountNumberUseCase getAccountByAccountNumberUseCase;

    public CreateAccountUseCase(AccountRepository accountRepository, GetAccountByAccountNumberUseCase getAccountByAccountNumberUseCase) {
        this.accountRepository = accountRepository;
        this.getAccountByAccountNumberUseCase = getAccountByAccountNumberUseCase;
    }

    public Mono<Account> apply(Account account) {
        return getAccountByAccountNumberUseCase.apply(account.getAccountNumber())
                .hasElement()
                .flatMap(hasElement -> {
                    if(hasElement) {
                        return Mono.error(new RuntimeException("Account already exists"));
                    }
                    return accountRepository.save(account);
                });
    }
}
