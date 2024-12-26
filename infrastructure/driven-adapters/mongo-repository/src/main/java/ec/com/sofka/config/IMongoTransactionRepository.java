package ec.com.sofka.config;

import ec.com.sofka.data.TransactionEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface IMongoTransactionRepository extends ReactiveMongoRepository<TransactionEntity, String> {
}
