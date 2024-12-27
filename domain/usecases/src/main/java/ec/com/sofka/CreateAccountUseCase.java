package ec.com.sofka;

import ec.com.sofka.gateway.AccountRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CreateAccountUseCase {
    private final AccountRepository accountRepository;

    public CreateAccountUseCase(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Mono<Account> apply(Account account) {
        return accountRepository.findByAccountNumber(account.getAccountNumber())
                .hasElement()
                .flatMap(hasElement -> {
                    if(hasElement) {
                        return Mono.error(new RuntimeException("Account already exists"));
                    }
                    return accountRepository.save(account);
                });
    }
}
