package ec.com.sofka.repository;

import ec.com.sofka.document.AccountEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IMongoRepository {

    public AccountEntity findById(String id);
}
