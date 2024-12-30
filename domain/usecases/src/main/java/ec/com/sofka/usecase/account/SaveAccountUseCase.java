package ec.com.sofka.usecase.account;

import ec.com.sofka.Account;
import ec.com.sofka.Customer;
import ec.com.sofka.Log;
import ec.com.sofka.gateway.AccountBusMessageGateway;
import ec.com.sofka.gateway.AccountRepositoryGateway;
import ec.com.sofka.gateway.CustomerRepositoryGateway;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

public class SaveAccountUseCase {

    private final AccountRepositoryGateway accountRepositoryGateway;
    private final AccountBusMessageGateway accountBusMessageGateway;

    public SaveAccountUseCase(AccountRepositoryGateway accountRepositoryGateway, AccountBusMessageGateway accountBusMessageGateway) {
        this.accountRepositoryGateway = accountRepositoryGateway;
        this.accountBusMessageGateway = accountBusMessageGateway;
    }

    public Mono<Account> save(Account entidad){
        accountBusMessageGateway.sendMsg(new Log("Saving account: "+entidad.toString(), LocalDate.now()));
        return accountRepositoryGateway.save(entidad);
    }
}
