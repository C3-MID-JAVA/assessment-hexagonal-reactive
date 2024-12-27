package ec.com.sofka;

import ec.com.sofka.config.TransactionMongoRepository;
import ec.com.sofka.data.TransactionEntity;
import ec.com.sofka.gateway.TransactionRepository;
import ec.com.sofka.mapper.TransactionMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class TransactionAdapter  implements  TransactionRepository{

    private final TransactionMongoRepository transactionMongoRepository;

    public TransactionAdapter(TransactionMongoRepository transactionMongoRepository) {
        this.transactionMongoRepository = transactionMongoRepository;
    }


    @Override
    public Flux<Transaction> findAll() {
        return transactionMongoRepository.findAll().map(TransactionMapper::toTransaction);
    }

    @Override
    public Mono<Transaction> save(Transaction transaction) {
        return transactionMongoRepository.save(TransactionMapper.toTransactionEntity(transaction))
                .map(TransactionMapper::toTransaction);
    }

    @Override
    public Flux<Transaction> findByAccountNumber(String accountNumber) {
        return transactionMongoRepository.findByAccountNumber(accountNumber)
                .map(TransactionMapper::toTransaction);
    }
}
