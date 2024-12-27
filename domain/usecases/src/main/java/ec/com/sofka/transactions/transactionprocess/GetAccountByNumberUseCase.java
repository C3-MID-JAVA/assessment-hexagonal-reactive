package ec.com.sofka.transactions.transactionprocess;

import ec.com.sofka.Account;
import ec.com.sofka.gateway.IAccountRepository;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;

public class GetAccountByNumberUseCase {
    private final IAccountRepository repository;

    public GetAccountByNumberUseCase(IAccountRepository repository) {
        this.repository = repository;
    }

    public Mono<Account> apply(String accountNumber) {
        return repository.findByAccountNumber(accountNumber)
                .switchIfEmpty(Mono.error(new NoSuchElementException("Cuenta no encontrada con el numero de Cuenta: " + accountNumber)));
    }
}
