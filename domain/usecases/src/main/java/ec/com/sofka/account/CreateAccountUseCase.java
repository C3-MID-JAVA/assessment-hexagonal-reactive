package ec.com.sofka.account;

import ec.com.sofka.Account;
import ec.com.sofka.gateway.AccountRepository;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.UUID;

public class CreateAccountUseCase {

    private final AccountRepository repository;

    public CreateAccountUseCase(AccountRepository repository) {
        this.repository = repository;
    }

    public Mono<Account> apply(Account account){
        account.setBalance(BigDecimal.valueOf(0.0));
        account.setAccountNumber(UUID.randomUUID().toString().substring(0,8));
        return repository.create(account);
    }
}
