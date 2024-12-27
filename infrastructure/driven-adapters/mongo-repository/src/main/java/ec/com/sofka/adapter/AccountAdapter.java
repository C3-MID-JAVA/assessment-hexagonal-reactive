package ec.com.sofka.adapter;

import ec.com.sofka.Account;
import ec.com.sofka.data.AccountDocument;
import ec.com.sofka.data.CustomerDocument;
import ec.com.sofka.gateway.AccountRepository;
import ec.com.sofka.mapper.*;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class AccountAdapter implements AccountRepository {

    private final ReactiveMongoTemplate reactiveMongoTemplate;

    public AccountAdapter(ReactiveMongoTemplate reactiveMongoTemplate) {
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }

    @Override
    public Mono<Account> createAccount(Mono<Account> account) {
        return account.flatMap(acc -> {
            Query query = new Query(Criteria.where("_id").is(acc.getId()));
            Update update = new Update().push("accounts", ModelToDocumentMapper.toAccount.apply(Mono.just(acc)));
            return reactiveMongoTemplate.updateFirst(query, update, CustomerDocument.class)
                    .flatMap(result -> result.getModifiedCount() > 0
                            ? Mono.just(acc)
                            : Mono.error(new RuntimeException("Failed to create account")));
        });
    }

    @Override
    public Mono<Account> findAccountById(Mono<Integer> id) {
        return id.flatMap(accountId -> reactiveMongoTemplate.findById(accountId, AccountDocument.class))
                .flatMap(accountDocument -> DocumentToModelMapper.toAccount.apply(Mono.just(accountDocument)));
    }

    @Override
    public Flux<Account> getAccountsByCustomerId(Mono<Integer> customer_id) {
        return customer_id.flatMapMany(customerId -> {
            Query query = new Query(Criteria.where("_id").is(customerId));
            return reactiveMongoTemplate.findOne(query, AccountDocument.class)
                    .flatMap(accountDocument ->  DocumentToModelMapper
                            .toAccount.apply(Mono.just(accountDocument)));
        });
    }

    @Override
    public Flux<Account> findAll() {
        return reactiveMongoTemplate.findAll(AccountDocument.class)
                .flatMap(accountDocument ->
                        DocumentToModelMapper.toAccount
                                .apply(Mono.just(accountDocument)));
    }

    @Override
    public Mono<Account> updateAccount(Mono<Account> account) {
        return account.flatMap(acc -> {
            Query query = new Query(Criteria.where("_id").is(acc.getId()));
            Update update = new Update()
                    .set("accounts.$.number", acc.getAccountNumber())
                    .set("accounts.$.type", acc.getAccountType())
                    .set("accounts.$.balance", acc.getBalance());
            return reactiveMongoTemplate.updateFirst(query, update, AccountDocument.class)
                    .flatMap(result -> result.getModifiedCount() > 0
                            ? Mono.just(acc)
                            : Mono.error(new RuntimeException("Account not found or failed to update")));
        });
    }

    @Override
    public Mono<Void> deleteById(Mono<Integer> id) {
        return id.flatMap(accountId -> reactiveMongoTemplate
                        .remove(Query.query(Criteria.where("_id").is(accountId)), AccountDocument.class))
                .then();
    }
}
