package ec.com.sofka.usecase.transaction;

import ec.com.sofka.Account;
import ec.com.sofka.Customer;
import ec.com.sofka.Log;
import ec.com.sofka.Transaction;
import ec.com.sofka.gateway.AccountRepositoryGateway;
import ec.com.sofka.gateway.CustomerRepositoryGateway;
import ec.com.sofka.gateway.TransactionBusMessageGateway;
import ec.com.sofka.gateway.TransactionRepositoryGateway;
import ec.com.sofka.usecase.exception.InsufficientBalanceException;
import ec.com.sofka.usecase.exception.ResourceNotFoundException;
import ec.com.sofka.usecase.util.TransactionCost;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.function.Function;
import java.util.function.Predicate;

public class SaveTransactionUseCase {

    private final TransactionRepositoryGateway transactionRepositoryGateway;
    private final AccountRepositoryGateway accountRepositoryGateway;
    private final TransactionBusMessageGateway transactionBusMessageGateway;

    public SaveTransactionUseCase(TransactionRepositoryGateway transactionRepositoryGateway, AccountRepositoryGateway accountRepositoryGateway, TransactionBusMessageGateway transactionBusMessageGateway) {
        this.transactionRepositoryGateway = transactionRepositoryGateway;
        this.accountRepositoryGateway = accountRepositoryGateway;
        this.transactionBusMessageGateway = transactionBusMessageGateway;
    }

    public Mono<Transaction> performTransaction(Transaction transaction, double transactionCost, boolean isDeposit) {
        BigDecimal transactionCostBD = new BigDecimal(transactionCost);

        Function<Account, BigDecimal> calculateAffectedBalance = account -> {
            BigDecimal amount = transaction.getAmount();
            return isDeposit
                    ? amount.subtract(transactionCostBD)
                    : amount.negate().subtract(transactionCostBD);
        };

        Predicate<BigDecimal> validBalance = balance -> balance.compareTo(BigDecimal.ZERO) >= 0;

        return accountRepositoryGateway.findById(transaction.getAccountId())
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Account not found with ID: " + transaction.getAccountId())))
                .flatMap(account -> {
                    BigDecimal affectedBalance = calculateAffectedBalance.apply(account);

                    if (!validBalance.test(account.getBalance().add(affectedBalance))) {
                        return Mono.error(new InsufficientBalanceException("Insufficient balance for the transaction."));
                    }

                    account.setBalance(account.getBalance().add(affectedBalance));

                    transactionBusMessageGateway.sendMsg(new Log("Saving transaction: "+transaction.toString(), LocalDate.now()));

                    return accountRepositoryGateway.save(account)
                            .then(transactionRepositoryGateway.save(transaction));
                });
    }
}
