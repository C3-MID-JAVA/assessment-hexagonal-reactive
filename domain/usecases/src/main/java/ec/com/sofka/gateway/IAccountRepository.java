package ec.com.sofka.gateway;

import ec.com.sofka.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IAccountRepository {
    Mono<Account> create(Account account);
    Flux<Account> getAllAccounts();
    Mono<Account> findAccountByNumber(String accountNumber);
}