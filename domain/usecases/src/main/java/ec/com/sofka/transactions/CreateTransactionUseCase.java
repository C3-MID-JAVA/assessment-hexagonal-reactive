package ec.com.sofka.transactions;

import ec.com.sofka.Account;
import ec.com.sofka.ConflictException;
import ec.com.sofka.Transaction;
import ec.com.sofka.gateway.ITransactionRepository;
import reactor.core.publisher.Mono;

public class CreateTransactionUseCase {

    private final ITransactionRepository repository;

    public CreateTransactionUseCase(ITransactionRepository repository) {
        this.repository = repository;
    }


    public Mono<Transaction> apply(Transaction transaction) {
        return repository.save(transaction, TipoOperacion.DEPOSITO);
    }

    public Mono<Account> apply(Account account) {
        return repository.findByAccountNumber(account.getAccountNumber())
                .flatMap(cuenta -> Mono.<Account>error(new ConflictException("The account number is already registered.")))
                .switchIfEmpty(repository.save(account));
    }

}
