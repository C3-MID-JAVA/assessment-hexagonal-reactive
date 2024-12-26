package ec.com.sofka.transactions;

import ec.com.sofka.Account;
import ec.com.sofka.Transaction;
import ec.com.sofka.gateway.ITransactionRepository;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;

public class GetTransactionByIdUseCase {

    private final ITransactionRepository repository;

    public GetTransactionByIdUseCase(ITransactionRepository repository) {
        this.repository = repository;
    }

    public Mono<Transaction> apply(String id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new NoSuchElementException("No transaction found with id: " + id)));

    }
}
