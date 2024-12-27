package co.com.sofka.bankingaccount.bankingaccount.domain.useCases.gateway;

import co.com.sofka.bankingaccount.bankingaccount.domain.model.Account;
import reactor.core.publisher.Mono;

public interface AccountRepository {
    Mono<Account> save(Account account);
    Mono<Account> findById(String id);
}
