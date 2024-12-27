package co.com.sofkau.config;


import co.com.sofkau.data.AccountEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;


public interface IAccountMongoRepository extends ReactiveMongoRepository<AccountEntity, String> {
    Mono<AccountEntity> findByAccountNumber(String accountNumber);
}
