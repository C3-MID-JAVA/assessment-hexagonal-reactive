package co.com.sofkau.config;

import co.com.sofkau.data.TransactionEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ITransactionMongoRepository extends ReactiveMongoRepository<TransactionEntity, String> {
}
