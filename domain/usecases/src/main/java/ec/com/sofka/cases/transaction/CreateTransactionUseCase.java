package ec.com.sofka.cases.transaction;

import ec.com.sofka.ITransactionCostStrategy;
import ec.com.sofka.Transaction;
import ec.com.sofka.factory.StrategyFactory;
import ec.com.sofka.gateway.IAccountRepository;
import ec.com.sofka.gateway.ITransactionRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
public class CreateTransactionUseCase {

    private final IAccountRepository accountRepository;
    private final ITransactionRepository transactionRepository;
    private final StrategyFactory strategyFactory;

    public CreateTransactionUseCase(IAccountRepository accountRepository, ITransactionRepository transactionRepository, StrategyFactory strategyFactory) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.strategyFactory = strategyFactory;
    }

    public Mono<Transaction> create(Transaction transaction) {
        return accountRepository.findAccountByNumber(transaction.getAccountId())
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Account not found")))
                .flatMap(account -> {
                    ITransactionCostStrategy strategy = strategyFactory.getStrategy(transaction.getType());
                    BigDecimal transactionCost = strategy.calculateCost(transaction.getAmount());
                    BigDecimal newBalance = account.getBalance().subtract(transaction.getAmount().add(transactionCost));

                    if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
                        return Mono.error(new IllegalArgumentException("Insufficient funds"));
                    }

                    account.setBalance(newBalance);
                    return accountRepository.create(account)
                            .then(transactionRepository.createTransaction(transaction));
                });
    }
}
