package ec.com.sofka.transactions;

import ec.com.sofka.Account;
import ec.com.sofka.Transaction;
import ec.com.sofka.gateway.IAccountRepository;
import ec.com.sofka.gateway.ITransactionRepository;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;

public class GetTransactionByAccountNumberUseCase {

    private final ITransactionRepository repository;

    public GetTransactionByAccountNumberUseCase(ITransactionRepository repository) {
        this.repository = repository;
    }

    public Mono<Transaction> apply(String accountNumber) {
        return repository.findByAccountNumber(accountNumber).switchIfEmpty(Mono.error(new NoSuchElementException("The account with the provided account number does not exist")));
    }

}
