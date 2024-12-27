package ec.com.sofka.adapter;

import ec.com.sofka.Account;
import ec.com.sofka.document.AccountEnti;
import ec.com.sofka.gateway.AccountRepositoryGateway;
import ec.com.sofka.mapper.AccountRepoMapper;
import ec.com.sofka.repository.AccountRepository;
import ec.com.sofka.repository.ClientRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class AccountAdapter implements AccountRepositoryGateway {

    private final AccountRepository accountRepository;

    public AccountAdapter(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }



    @Override
    public Mono<Account> save(Account account) {
        AccountEnti entity = AccountRepoMapper.toEntity(account);
        return accountRepository.save(entity)
                .map(AccountRepoMapper::toDomain);
    }

    @Override
    public Mono<Account> findById(String id) {
        return accountRepository.findById(id)
                .map(AccountRepoMapper::toDomain);
    }

    @Override
    public Flux<Account> findAll() {
        return accountRepository.findAll()
                .map(AccountRepoMapper::toDomain);
    }
}
