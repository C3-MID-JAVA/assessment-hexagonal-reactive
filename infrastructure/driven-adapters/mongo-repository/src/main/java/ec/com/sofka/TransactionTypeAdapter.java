package ec.com.sofka;

import ec.com.sofka.config.ITransactionTypeRepository;
import ec.com.sofka.gateway.TransactionTypeRepository;
import ec.com.sofka.mapper.TransactionTypeEntityMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class TransactionTypeAdapter implements TransactionTypeRepository {
    private final ITransactionTypeRepository transactionTypeRepository;

    public TransactionTypeAdapter(ITransactionTypeRepository transactionTypeRepository) {
        this.transactionTypeRepository = transactionTypeRepository;
    }

    @Override
    public Mono<TransactionType> save(TransactionType transactionType) {
        return transactionTypeRepository.save(TransactionTypeEntityMapper.mapToEntity(transactionType))
                        .map(TransactionTypeEntityMapper::mapToModel);
    }

    @Override
    public Flux<TransactionType> findAll() {
        return transactionTypeRepository.findAll()
                .map(TransactionTypeEntityMapper::mapToModel);
    }

    @Override
    public Mono<TransactionType> findById(String transactionTypeId) {
        return transactionTypeRepository.findById(transactionTypeId)
                .map(TransactionTypeEntityMapper::mapToModel);
    }

    @Override
    public Mono<TransactionType> findByType(String transactionType) {
        return transactionTypeRepository.findByType(transactionType)
                .map(TransactionTypeEntityMapper::mapToModel);
    }

}