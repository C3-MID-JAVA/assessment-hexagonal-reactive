package ec.com.sofka.repository;

import ec.com.sofka.document.AccountEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAccountRepository extends ReactiveMongoRepository<AccountEntity, String> {
}
