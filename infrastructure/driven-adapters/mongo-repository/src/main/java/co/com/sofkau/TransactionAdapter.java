package co.com.sofkau;

import co.com.sofkau.config.ITransactionMongoRepository;
import co.com.sofkau.gateway.ITransactionRepository;
import co.com.sofkau.mapper.TransactionMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class TransactionAdapter implements ITransactionRepository {

    private final ITransactionMongoRepository transactionMongoRepository;

    public TransactionAdapter(ITransactionMongoRepository transactionMongoRepository) {
        this.transactionMongoRepository = transactionMongoRepository;
    }


    @Override
    public Mono<Transaction> save(Transaction transaction) {
        return transactionMongoRepository.save(TransactionMapper.toTransactionEntity(transaction)).map(TransactionMapper::toTransaction);
    }
}
