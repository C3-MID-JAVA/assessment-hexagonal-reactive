package ec.com.sofka.usecase.account;

import ec.com.sofka.Account;
import ec.com.sofka.Customer;
import ec.com.sofka.gateway.AccountRepositoryGateway;
import ec.com.sofka.gateway.BusMessageGateway;
import ec.com.sofka.gateway.CustomerRepositoryGateway;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class FindAccountUseCase {

    private final AccountRepositoryGateway accountRepositoryGateway;
    private final BusMessageGateway busMessageGateway;

    public FindAccountUseCase(AccountRepositoryGateway accountRepositoryGateway, BusMessageGateway busMessageGateway) {
        this.accountRepositoryGateway = accountRepositoryGateway;
        this.busMessageGateway = busMessageGateway;
    }

    public Mono<Account> findById(String id) {
        busMessageGateway.sendMsg("Searching for account by ID: " + id);
        return accountRepositoryGateway.findById(id);
    }

    public Flux<Account> findAll() {
        busMessageGateway.sendMsg("Search all account");
        return accountRepositoryGateway.findAll();
    }
}
