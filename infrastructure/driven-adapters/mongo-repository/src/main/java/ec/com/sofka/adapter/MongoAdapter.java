package ec.com.sofka.adapter;

import ec.com.sofka.Account;
import ec.com.sofka.repository.IMongoRepository;
import ec.com.sofka.document.AccountEntity;
import ec.com.sofka.gateway.AccountRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class MongoAdapter implements AccountRepository {

//    private final IMongoRepository repository;
//
//    public MongoAdapter(IMongoRepository repository) {
//        this.repository = repository;
//    }

    @Override
    public Account findByAcccountId(String id) {
        //AccountEntity found = repository.findById(id).get();

        //return new Account(found.getId(), found.getBalance(), found.getOwner(), found.getAccountNumber());
        return null;
    }

    @Override
    public Mono<Account> save(Account account) {
        return null;
    }

    @Override
    public Mono<Account> findByAccountNumber(String accountNumber) {
        return null;
    }

    @Override
    public Flux<Account> findAll() {
        return null;
    }

    @Override
    public Mono<Void> deleteByAccountNumber(String accountNumber) {
        return null;
    }
}
