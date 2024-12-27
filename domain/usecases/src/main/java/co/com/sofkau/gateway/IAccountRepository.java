package co.com.sofkau.gateway;

import co.com.sofkau.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface IAccountRepository {
    Mono<Account> findByAccountNumber(String accountNumber);
    Flux<Account> findAllAccounts();
    Mono<Account> save(Account account);
}
