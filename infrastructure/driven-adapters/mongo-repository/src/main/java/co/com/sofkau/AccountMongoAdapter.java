package co.com.sofkau;

import co.com.sofkau.config.IAccountMongoRepository;
import co.com.sofkau.data.AccountEntity;
import co.com.sofkau.gateway.IAccountRepository;
import co.com.sofkau.mapper.AccountMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class AccountMongoAdapter implements IAccountRepository {

    private final IAccountMongoRepository accountMongoRepository;

    public AccountMongoAdapter(IAccountMongoRepository accountMongoRepository) {
        this.accountMongoRepository = accountMongoRepository;
    }

    @Override
    public Mono<Account> findByAccountNumber(String accountNumber) {
        return accountMongoRepository.findByAccountNumber(accountNumber).map(AccountMapper::toAccount);
    }

    @Override
    public Flux<Account> findAllAccounts() {
        return accountMongoRepository.findAll().map(AccountMapper::toAccount);
    }

    @Override
    public Mono<Account> save(Account account) {
        return accountMongoRepository.save(AccountMapper.toAccountEntity(account)).map(AccountMapper::toAccount);
    }
}
