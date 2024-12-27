package ec.com.sofka.gateway;

import ec.com.sofka.Account;
import reactor.core.publisher.Mono;

public interface AccountRepository {
    Mono<Account> findByAcccountId(String id);
    Mono<Account> CreateAcccount(Account account);
}
