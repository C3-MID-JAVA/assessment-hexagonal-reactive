package ec.com.sofka;

import ec.com.sofka.config.AccountMongoRepository;
import ec.com.sofka.data.AccountEntity;
import ec.com.sofka.gateway.AccountRepository;
import ec.com.sofka.mapper.AccountMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Repository
public class AccountAdapter implements AccountRepository {

    private final AccountMongoRepository accountMongoRepository;

    public AccountAdapter(AccountMongoRepository accountMongorepository) {
        this.accountMongoRepository = accountMongorepository;
    }


    @Override
    public Flux<Account> findAll() {
        return accountMongoRepository.findAll().map(AccountMapper::toAccount);
    }

    @Override
    public Mono<Account> findByAccountNumber(String accountNumber) {
        return accountMongoRepository.findByAccountNumber(accountNumber).map(AccountMapper::toAccount);
    }

    @Override
    public Mono<Account> save(Account account) {
        return accountMongoRepository.save(AccountMapper.toAccountEntity(account)).map(AccountMapper::toAccount);
    }
}
