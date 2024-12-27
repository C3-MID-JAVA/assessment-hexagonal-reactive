package co.com.sofka.bankingaccount.bankingaccount.infrastructure.drivenAdapters.mongoRepository.config;

import co.com.sofka.bankingaccount.bankingaccount.infrastructure.drivenAdapters.mongoRepository.entities.AccountEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface IAccountRepository extends ReactiveMongoRepository<AccountEntity, String> {

}
