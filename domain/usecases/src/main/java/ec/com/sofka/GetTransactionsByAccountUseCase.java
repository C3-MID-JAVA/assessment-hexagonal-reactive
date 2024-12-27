package ec.com.sofka;

import ec.com.sofka.gateway.AccountRepository;
import ec.com.sofka.gateway.TransactionRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class GetTransactionsByAccountUseCase {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public GetTransactionsByAccountUseCase(TransactionRepository transactionRepository, AccountRepository accountRepository){
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    public Flux<Transaction> apply(String accountNumber){
        return accountRepository.findByAccountNumber(accountNumber)
                .switchIfEmpty(Mono.error(new RuntimeException("No transactions found with account number" + accountNumber)))
                .flatMapMany(account -> {
                    return transactionRepository.findByAccountNumber(account.getAccountNumber());
                });
    }
}
