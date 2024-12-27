package co.com.sofkau;


import co.com.sofkau.gateway.IAccountRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Component
public class UpdateAccountUseCase {
    private final IAccountRepository accountRepository;


    public UpdateAccountUseCase(IAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Mono<Account> apply(Account account) {
        return accountRepository.save(account);
    }
}
