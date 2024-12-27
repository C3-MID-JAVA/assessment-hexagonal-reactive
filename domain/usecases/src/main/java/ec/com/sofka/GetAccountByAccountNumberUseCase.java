package ec.com.sofka;

import ec.com.sofka.gateway.AccountRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Component
public class GetAccountByAccountNumberUseCase {

    private final AccountRepository accountRepository;

    public GetAccountByAccountNumberUseCase(AccountRepository accountRepository) {

        this.accountRepository = accountRepository;
    }

    public Mono<Account> apply(String accountNumber){
        return accountRepository.findByAccountNumber(accountNumber).switchIfEmpty(Mono.empty());
    }
}
