package ec.com.sofka.config;

import ec.com.sofka.data.AccountEntity;
import ec.com.sofka.data.TransactionsEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface IMongoTransactionRepository extends ReactiveMongoRepository<TransactionsEntity, String> {

}
