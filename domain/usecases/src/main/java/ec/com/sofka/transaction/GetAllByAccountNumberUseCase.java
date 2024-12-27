package ec.com.sofka.transaction;

import ec.com.sofka.Transaction;
import ec.com.sofka.exception.NotFoundException;
import ec.com.sofka.gateway.AccountRepository;
import ec.com.sofka.gateway.TransactionRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class GetAllByAccountNumberUseCase {

    private final TransactionRepository repository;
    private final AccountRepository accountRepository;

    public GetAllByAccountNumberUseCase(TransactionRepository repository, AccountRepository accountRepository) {
        this.repository = repository;
        this.accountRepository = accountRepository;
    }

    public Flux<Transaction> apply(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .switchIfEmpty(Mono.error(new NotFoundException("Account not found")))
                .flatMapMany(account ->  {
                    return repository.getAllByAccountId(account.getId());
                });
    }
}
