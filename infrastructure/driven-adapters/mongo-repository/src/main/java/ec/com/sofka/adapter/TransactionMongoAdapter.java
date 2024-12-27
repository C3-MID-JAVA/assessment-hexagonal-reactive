package ec.com.sofka.adapter;

import ec.com.sofka.Transaction;
import ec.com.sofka.config.TransactionMongoRepository;
import ec.com.sofka.data.TransactionEntity;
import ec.com.sofka.gateway.TransactionRepository;
import ec.com.sofka.mapper.TransactionMapperEntity;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class TransactionMongoAdapter implements TransactionRepository {

    private final TransactionMongoRepository repository;

    public TransactionMongoAdapter(TransactionMongoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<Transaction> create(Transaction transaction) {
        TransactionEntity transactionEntity = TransactionMapperEntity.toEntity(transaction);
        return repository.save(transactionEntity).map(TransactionMapperEntity::fromEntity);
    }

    @Override
    public Flux<Transaction> getAllByAccountId(String accountId) {
        return repository.findAllByAccountId(accountId)
                .map(TransactionMapperEntity::fromEntity);
    }
}
