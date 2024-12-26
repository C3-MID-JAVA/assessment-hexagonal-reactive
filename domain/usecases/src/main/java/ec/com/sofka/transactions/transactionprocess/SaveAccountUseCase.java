package ec.com.sofka.transactions.transactionprocess;

import ec.com.sofka.Account;
import ec.com.sofka.gateway.IAccountRepository;
import reactor.core.publisher.Mono;

public class SaveAccountUseCase {

    private final IAccountRepository repository;

    public SaveAccountUseCase(IAccountRepository repository) {
        this.repository = repository;
    }

    public Mono<Account> apply(Account account) {
        return repository.save(account);
    }
}
