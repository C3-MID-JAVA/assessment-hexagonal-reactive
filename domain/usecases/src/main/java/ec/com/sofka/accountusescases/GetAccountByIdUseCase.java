package ec.com.sofka.accountusescases;

import ec.com.sofka.Account;
import ec.com.sofka.gateway.AccountRepository;
import reactor.core.publisher.Mono;


public class GetAccountByIdUseCase{

    private final AccountRepository repository;

    public GetAccountByIdUseCase(AccountRepository repository) {
        this.repository = repository;
    }

    public Mono<Account> apply(String id){
        return repository.findByAcccountId(id);
    }

}
