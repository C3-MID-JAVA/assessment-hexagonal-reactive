package co.com.sofka.bankingaccount.bankingaccount.infrastructure.drivenAdapters.mongoRepository;

import co.com.sofka.bankingaccount.bankingaccount.domain.model.Account;
import co.com.sofka.bankingaccount.bankingaccount.domain.useCases.gateway.AccountRepository;
import co.com.sofka.bankingaccount.bankingaccount.infrastructure.drivenAdapters.mongoRepository.config.IAccountRepository;
import co.com.sofka.bankingaccount.bankingaccount.infrastructure.persistence.mapper.AccountMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class AccountAdapter implements AccountRepository {
    private final IAccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public AccountAdapter(IAccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    @Override
    public Mono<Account> save(Account account) {
        return accountRepository.save(accountMapper.toAccountEntity(account))
                .map(accountMapper::toAccount);
    }

    @Override
    public Mono<Account> findById(String id) {
        return accountRepository.findById(id)
                .map(accountMapper::toAccount)
                .switchIfEmpty(Mono.error(new RuntimeException("Account not found!")));
    }
}
