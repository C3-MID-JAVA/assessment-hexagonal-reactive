package ec.com.sofka;

import ec.com.sofka.gateway.IAccountRepository;
import ec.com.sofka.config.IMongoAccountRepository;
import ec.com.sofka.mapper.AccountMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Repository
public class AccountAdapter implements IAccountRepository {

    private final IMongoAccountRepository repository;

    public AccountAdapter(IMongoAccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<Account> findByAccountNumber(String accountNumber) {
        return repository.findByAccountNumber(accountNumber)
                .map(AccountMapper::toAccount
                );
    }

    @Override
    public Mono<Account> save(Account account) {
        return repository.save(AccountMapper.toAccountEntity(account)).map(AccountMapper::toAccount);
    }

    @Override
    public Flux<Account> findAll() {
        return repository.findAll().map(AccountMapper::toAccount);
    }

    @Override
    public Mono<Account> findById(String id) {
        return repository.findById(id).map(AccountMapper::toAccount);
    }

    @Override
    public Mono<BigDecimal> getCheckBalance(String id) {
        return repository.findById(id)
                .flatMap(account -> {
                    if (account.getBalance() == null) {
                        return Mono.error(new RuntimeException("Balance is null for account ID: " + id));
                    }
                    return Mono.just(account.getBalance());
                });
    }
}
