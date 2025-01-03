package ec.com.sofka.adapter;

import ec.com.sofka.Transaction;
import ec.com.sofka.config.ITransactionMongoRepository;
import ec.com.sofka.gateway.ITransactionRepository;
import ec.com.sofka.mapper.TransactionModelMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class TransactionMongoAdapter implements ITransactionRepository {

    private final ITransactionMongoRepository transactionMongoRepository;

    public TransactionMongoAdapter(ITransactionMongoRepository transactionMongoRepository) {
        this.transactionMongoRepository = transactionMongoRepository;
    }

    @Override
    public Mono<Transaction> createTransaction(Transaction transaction) {
        return transactionMongoRepository.save(TransactionModelMapper.toEntity(transaction))
                .map(TransactionModelMapper::fromEntity);
    }

    @Override
    public Flux<Transaction> getAllTransactions() {
        return transactionMongoRepository.findAll()
                .map(TransactionModelMapper::fromEntity);
    }
}