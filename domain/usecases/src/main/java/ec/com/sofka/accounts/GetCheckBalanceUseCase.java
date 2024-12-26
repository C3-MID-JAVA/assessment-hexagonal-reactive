package ec.com.sofka.accounts;

import ec.com.sofka.Account;
import ec.com.sofka.gateway.IAccountRepository;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

public class GetCheckBalanceUseCase {
    private final IAccountRepository repository;

    public GetCheckBalanceUseCase(IAccountRepository repository) {
        this.repository = repository;
    }

    public Mono<BigDecimal> apply (String id){
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new NoSuchElementException("No account found with id: " + id)))
                .map(Account::getBalance);
    }
}
