package co.com.sofkau;

import co.com.sofkau.gateway.IAccountRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Component
public class GetAllAccountsUseCase  {

   private final IAccountRepository accountRepository;

    public GetAllAccountsUseCase(IAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Flux<Account> apply(){
        return accountRepository.findAllAccounts();
    }
}
