package ec.com.sofka.cases.account;

import ec.com.sofka.Account;
import ec.com.sofka.gateway.IAccountRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CreateAccountUseCase {

    private final IAccountRepository accountRepository;

    public CreateAccountUseCase(IAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Mono<Account> create(Account account) {
        return accountRepository.findAccountByNumber(account.getAccountNumber())
                .flatMap(existingAccount -> Mono.error(new IllegalArgumentException(
                        "Account with number " + account.getAccountNumber() + " already exists")))
                .switchIfEmpty(Mono.defer(() -> accountRepository.create(account)))
                .then(Mono.defer(() -> accountRepository.findAccountByNumber(account.getAccountNumber())))
                .switchIfEmpty(Mono.error(new IllegalStateException("Account creation failed unexpectedly")));
    }
}
