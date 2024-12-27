package ec.com.sofka.UC.get.account;

import ec.com.sofka.Account;
import ec.com.sofka.gateway.AccountRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class GetAccountsByCustomerIdUseCase {
    private final AccountRepository repository;

    public GetAccountsByCustomerIdUseCase(AccountRepository repository) {
        this.repository = repository;
    }

    public Flux<Account> apply(Integer customerId) {
        return repository.getAccountsByCustomerId(Mono.just(customerId));
    }
}
