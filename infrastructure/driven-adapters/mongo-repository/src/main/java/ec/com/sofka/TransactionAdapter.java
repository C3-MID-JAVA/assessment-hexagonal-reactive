package ec.com.sofka;

import ec.com.sofka.config.IMongoTransactionRepository;
import ec.com.sofka.gateway.ITransactionRepository;
import ec.com.sofka.mapper.AccountMapper;
import ec.com.sofka.mapper.TransactionMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class TransactionAdapter implements ITransactionRepository {
    private final IMongoTransactionRepository repository;

    public TransactionAdapter(IMongoTransactionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Flux<Transaction> findAll() {
        return repository.findAll().map(TransactionMapper::transactionEntityToTransaction);
    }

    @Override
    public Mono<Transaction> save(Transaction transaction) {
        return repository.save(TransactionMapper.transactionToAccountEntity(transaction))
                .map(TransactionMapper::transactionEntityToTransaction);
    }

    @Override
    public Mono<Transaction> findById(String id) {
        return repository.findById(id).map(TransactionMapper::transactionEntityToTransaction);
    }

}
