package ec.com.sofka;

import ec.com.sofka.gateway.AccountRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Component
public class GetAllAccountsUseCase {
    private final AccountRepository accountRepository;

    public GetAllAccountsUseCase(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Flux<Account> apply(){
        return accountRepository.findAll();
    }
}
