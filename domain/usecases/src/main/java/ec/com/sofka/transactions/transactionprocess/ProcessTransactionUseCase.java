package ec.com.sofka.transactions.transactionprocess;

import ec.com.sofka.ConflictException;
import ec.com.sofka.Transaction;
import ec.com.sofka.enums.OperationType;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.function.Predicate;

public class ProcessTransactionUseCase {

    private final GetAccountByNumberUseCase getAccountByNumberUseCase;
    private final GetTransactionStrategyUseCase getTransactionStrategyUseCase;
    private final CalculateFinalBalanceUseCase calculateFinalBalanceUseCase;
    private final SaveAccountUseCase saveAccountUseCase;
    private final SaveTransactionUseCase saveTransactionUseCase;

    private final Predicate<BigDecimal> isSaldoInsuficiente = saldo -> saldo.compareTo(BigDecimal.ZERO) < 0;

    public ProcessTransactionUseCase(GetAccountByNumberUseCase getAccountByNumberUseCase,
                                     GetTransactionStrategyUseCase getTransactionStrategyUseCase,
                                     CalculateFinalBalanceUseCase calculateFinalBalanceUseCase,
                                     SaveAccountUseCase saveAccountUseCase,
                                     SaveTransactionUseCase saveTransactionUseCase) {
        this.getAccountByNumberUseCase = getAccountByNumberUseCase;
        this.getTransactionStrategyUseCase = getTransactionStrategyUseCase;
        this.calculateFinalBalanceUseCase = calculateFinalBalanceUseCase;
        this.saveAccountUseCase = saveAccountUseCase;
        this.saveTransactionUseCase = saveTransactionUseCase;
    }

    public Mono<Transaction> apply(Transaction transaction, OperationType operationType) {
        return getAccountByNumberUseCase.apply(transaction.getAccountId())
                .flatMap(account -> getTransactionStrategyUseCase.apply(account, transaction.getType(), operationType,transaction.getAmount())
                        .flatMap(strategy -> {
                            BigDecimal finalBalance = calculateFinalBalanceUseCase.apply(
                                    account.getBalance(),
                                    transaction.getAmount(),
                                    strategy.getAmount(),
                                    operationType
                            );

                            if (isSaldoInsuficiente.test(finalBalance)) {
                                return Mono.error(new ConflictException("Insufficient balance for transaction."));
                            }

                            account.setBalance(finalBalance);
                            return saveAccountUseCase.apply(account)
                                    .flatMap(savedAccount -> {
                                        transaction.setAccountId(savedAccount.getId());
                                        transaction.setTransactionCost(strategy.getAmount());
                                        return saveTransactionUseCase.apply(transaction);
                                    });
                        }));
    }
}
