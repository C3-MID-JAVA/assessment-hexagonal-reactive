package ec.com.sofka.usecase.account;

import ec.com.sofka.Account;
import ec.com.sofka.Customer;
import ec.com.sofka.gateway.AccountRepositoryGateway;
import ec.com.sofka.gateway.CustomerRepositoryGateway;
import reactor.core.publisher.Mono;

public class SaveAccountUseCase {

    private final AccountRepositoryGateway accountRepositoryGateway;

    public SaveAccountUseCase(AccountRepositoryGateway accountRepositoryGateway) {
        this.accountRepositoryGateway = accountRepositoryGateway;
    }

    public Mono<Account> save(Account entidad){
        return accountRepositoryGateway.save(entidad);
    }
}
