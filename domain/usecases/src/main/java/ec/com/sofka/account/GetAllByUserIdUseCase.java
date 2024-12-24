package ec.com.sofka.account;

import ec.com.sofka.Account;
import ec.com.sofka.gateway.AccountRepository;
import reactor.core.publisher.Flux;

public class GetAllByUserIdUseCase {

    private final AccountRepository repository;

    public GetAllByUserIdUseCase(AccountRepository repository) {
        this.repository = repository;
    }

    public Flux<Account> apply(String userid){
        return repository.getAllByUserId(userid);
    }
}
