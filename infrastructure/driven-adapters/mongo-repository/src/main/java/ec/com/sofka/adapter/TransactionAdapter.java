package ec.com.sofka.adapter;

import ec.com.sofka.Transaction;
import ec.com.sofka.document.TransactionEntity;
import ec.com.sofka.gateway.TransactionRepositoryGateway;
import ec.com.sofka.mapper.TransactionRepoMapper;
import ec.com.sofka.repository.ITransactionRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class TransactionAdapter implements TransactionRepositoryGateway {

    private final ITransactionRepository transactionRepository;

    public TransactionAdapter(ITransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Mono<Transaction> save(Transaction transaction) {
        TransactionEntity transactionEntity = TransactionRepoMapper.toEntity(transaction);
        return transactionRepository.save(transactionEntity)
                .map(TransactionRepoMapper::toDomain);
    }

}
