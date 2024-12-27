package ec.com.sofka;
import ec.com.sofka.config.ITransactionMongoRepository;
import ec.com.sofka.gateway.TransactionRepository;
import ec.com.sofka.mappers.TransactionMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class MongoAdapterTransaction implements TransactionRepository {
    private final ITransactionMongoRepository repository;

    public MongoAdapterTransaction(ITransactionMongoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<Transaction> findTransactionById(String id) {
        return repository.findById(id)
                .map(TransactionMapper::toModel);
    }

    @Override
    public Mono<Transaction> saveTransaction(Transaction transaction) {
        return repository.save(TransactionMapper.toDocument(transaction))
                .map(TransactionMapper::toModel);
    }
}