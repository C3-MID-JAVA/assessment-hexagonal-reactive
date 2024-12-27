package ec.com.sofka.config;

import ec.com.sofka.data.AccountEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;


public interface IAccountMongoRepository extends ReactiveMongoRepository<AccountEntity, String> {
}
