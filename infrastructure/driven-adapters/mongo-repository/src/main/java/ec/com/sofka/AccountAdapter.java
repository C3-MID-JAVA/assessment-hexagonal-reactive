package ec.com.sofka;

import ec.com.sofka.config.IAccountRepository;
import ec.com.sofka.gateway.AccountRepository;
import ec.com.sofka.mapper.AccountEntityMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class AccountAdapter implements AccountRepository {
    private final IAccountRepository accountRepository;

    public AccountAdapter(IAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Mono<Account> save(Account account) {
        return accountRepository.save(AccountEntityMapper.mapToEntity(account))
                .map(AccountEntityMapper::mapToModel);
    }

    @Override
    public Mono<Account> findByNumber(String number) {
        return accountRepository.findByNumber(number)
                .map(AccountEntityMapper::mapToModel);
    }

    @Override
    public Flux<Account> findAll() {
        return accountRepository.findAll()
                .map(AccountEntityMapper::mapToModel);
    }

}