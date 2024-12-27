package ec.com.sofka;

import ec.com.sofka.gateway.TransactionRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class GetTransactionsByAccountUseCase {
    private final TransactionRepository transactionRepository;

    public GetTransactionsByAccountUseCase(TransactionRepository transactionRepository){
        this.transactionRepository = transactionRepository;
    }

    public Flux<Transaction> apply(String accountNumber){
        return transactionRepository.findByAccountNumber(accountNumber)
                .switchIfEmpty(Mono.error(new RuntimeException("No transactions found with account number" + accountNumber)));
    }
}
