package ec.com.sofka.cases.account;

import ec.com.sofka.Account;
import ec.com.sofka.gateway.IAccountRepository;
import reactor.core.publisher.Flux;
import org.springframework.stereotype.Component;

@Component
public class GetAllAccountsUseCase {
    private final IAccountRepository accountRepository;

    public GetAllAccountsUseCase(IAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Flux<Account> getAll() {
        return accountRepository.getAllAccounts();
    }
}