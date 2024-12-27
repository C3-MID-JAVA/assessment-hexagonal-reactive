package co.com.sofka.bankingaccount.bankingaccount.infrastructure.drivenAdapters.mongoRepository.config;

import co.com.sofka.bankingaccount.bankingaccount.infrastructure.drivenAdapters.mongoRepository.entities.transaction.TransactionEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface ITransactionRepository extends ReactiveMongoRepository<TransactionEntity, String> {
    Flux<TransactionEntity> findByAccountId(String accountId);
    Flux<TransactionEntity> findByType(String type);
}
