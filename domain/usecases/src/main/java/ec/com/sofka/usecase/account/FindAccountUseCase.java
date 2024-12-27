package ec.com.sofka.usecase.account;

import ec.com.sofka.Account;
import ec.com.sofka.Customer;
import ec.com.sofka.gateway.AccountRepositoryGateway;
import ec.com.sofka.gateway.CustomerRepositoryGateway;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class FindAccountUseCase {

    private final AccountRepositoryGateway accountRepositoryGateway;

    public FindAccountUseCase(AccountRepositoryGateway accountRepositoryGateway) {
        this.accountRepositoryGateway = accountRepositoryGateway;
    }

    public Mono<Account> findById(String id){
        return accountRepositoryGateway.findById(id);
    }

    public Flux<Account> findAll(){
        return accountRepositoryGateway.findAll();
    }
}
