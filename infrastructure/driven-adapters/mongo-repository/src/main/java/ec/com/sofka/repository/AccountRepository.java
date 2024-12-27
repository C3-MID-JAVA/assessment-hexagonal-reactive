package ec.com.sofka.repository;

import ec.com.sofka.document.AccountEnti;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends ReactiveMongoRepository<AccountEnti, String> {
}
