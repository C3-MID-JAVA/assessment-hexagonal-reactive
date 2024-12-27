package ec.com.sofka;

import ec.com.sofka.config.IAccountMongoRepository;
import ec.com.sofka.data.AccountEntity;
import ec.com.sofka.gateway.AccountRepository;
import ec.com.sofka.mappers.AccountMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class MongoAdapterAccount implements AccountRepository {
    private final IAccountMongoRepository repository;

    public MongoAdapterAccount(IAccountMongoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<Account> findByAccountById(String id) {
        return repository.findById(id)
                .map(AccountMapper::toModel);
    }

    @Override
    public Flux<Account> findAllAccounts() {
        return repository.findAll()
                .map(AccountMapper::toModel);
    }

    @Override
    public Mono<Account> saveAccount(Account account) {
        System.out.println("Guardando cuenta "+ account.getId()+" "+account.getBalance()+" "+account.getAccountNumber()+" "+account.getOwner());
        return repository.save(AccountMapper.toDocument(account))
                .map(AccountMapper::toModel);
    }
}