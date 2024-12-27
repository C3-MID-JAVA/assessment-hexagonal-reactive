package ec.com.sofka.adapter;

import ec.com.sofka.Account;
import ec.com.sofka.config.AccountMongoRepository;
import ec.com.sofka.data.AccountEntity;
import ec.com.sofka.gateway.AccountRepository;
import ec.com.sofka.mapper.AccountMapperEntity;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class AccountMongoAdapter implements AccountRepository {
    private final AccountMongoRepository repository;

    public AccountMongoAdapter(AccountMongoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<Account> create(Account account) {
        AccountEntity accountEntity = AccountMapperEntity.toEntity(account);
        return repository.save(accountEntity).map(AccountMapperEntity::fromEntity);
    }

    @Override
    public Flux<Account> getAllByUserId(String userId) {
        return repository.findByUserId(userId).map(AccountMapperEntity::fromEntity);
    }

    @Override
    public Mono<Account> findByAccountNumber(String accountNumber) {
        return repository.findByAccountNumber(accountNumber).map(AccountMapperEntity::fromEntity);
    }

    @Override
    public Mono<Account> findById(String id) {
        return repository.findById(id).map(AccountMapperEntity::fromEntity);
    }
}
