package ec.com.sofka.adapter;

import ec.com.sofka.Account;
import ec.com.sofka.config.IAccountMongoRepository;
import ec.com.sofka.gateway.IAccountRepository;
import ec.com.sofka.mapper.AccountModelMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class AccountMongoAdapter implements IAccountRepository {

    private final IAccountMongoRepository accountMongoRepository;

    public AccountMongoAdapter(IAccountMongoRepository accountMongoRepository) {
        this.accountMongoRepository = accountMongoRepository;
    }

    @Override
    public Mono<Account> create(Account account) {
        return accountMongoRepository.save(AccountModelMapper.toEntity(account))
                .map(AccountModelMapper::fromEntity);
    }

    @Override
    public Mono<Account> findAccountByNumber(String accountNumber) {
        return accountMongoRepository.findByAccountNumber(accountNumber)
                .map(AccountModelMapper::fromEntity);
    }

    @Override
    public Flux<Account> getAllAccounts() {
        return accountMongoRepository.findAll()
                .map(AccountModelMapper::fromEntity);
    }
}