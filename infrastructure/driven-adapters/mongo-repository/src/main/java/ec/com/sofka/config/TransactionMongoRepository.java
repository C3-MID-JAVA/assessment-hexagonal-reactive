package ec.com.sofka.config;

import ec.com.sofka.Transaction;
import ec.com.sofka.data.TransactionEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface TransactionMongoRepository extends ReactiveMongoRepository<TransactionEntity, String> {

}
