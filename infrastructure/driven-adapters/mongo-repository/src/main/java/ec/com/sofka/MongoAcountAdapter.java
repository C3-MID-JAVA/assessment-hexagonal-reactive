package ec.com.sofka;

import ec.com.sofka.config.IMongoAccountRepository;
import ec.com.sofka.data.AccountEntity;
import ec.com.sofka.gateway.AccountRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class MongoAcountAdapter implements AccountRepository {

    private final IMongoAccountRepository accountrepository;

    public MongoAcountAdapter(IMongoAccountRepository accountrepository) {
        this.accountrepository = accountrepository;
    }

    @Override
    public Mono<Account> findByAcccountId(String id) {
        return accountrepository.findById(id) // Devuelve un Mono<AccountEntity>
                .map(found -> new Account(
                        found.getId(),
                        found.getBalance(),
                        found.getOwner()
                ))
                .switchIfEmpty(Mono.empty());
    }

    @Override
    public Mono<Account> CreateAcccount(Account account) {
        return accountrepository.save(new AccountEntity(account.getBalance(), account.getOwner()))
                .map(savedEntity -> new Account(
                        savedEntity.getId(),
                        savedEntity.getBalance(),
                        savedEntity.getOwner()
                ));
    }


}
