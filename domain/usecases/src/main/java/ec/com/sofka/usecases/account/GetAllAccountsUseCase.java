package ec.com.sofka.usecases.account;

import ec.com.sofka.Account;
import ec.com.sofka.gateway.AccountRepository;
import reactor.core.publisher.Flux;

public class GetAllAccountsUseCase {
    private final AccountRepository accountRepository;

    public GetAllAccountsUseCase(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Flux<Account> apply(){
        return accountRepository.findAllAccounts()
                .switchIfEmpty(Flux.empty());
    }
}
