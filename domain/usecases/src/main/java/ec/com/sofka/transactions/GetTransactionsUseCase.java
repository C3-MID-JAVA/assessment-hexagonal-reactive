package ec.com.sofka.transactions;

import ec.com.sofka.Account;
import ec.com.sofka.Transaction;
import ec.com.sofka.gateway.ITransactionRepository;
import reactor.core.publisher.Flux;

public class GetTransactionsUseCase {

    private final ITransactionRepository repository;

    public GetTransactionsUseCase(ITransactionRepository repository) {
        this.repository = repository;
    }

    public Flux<Transaction> apply() {
        return repository.findAll();
    }
}
