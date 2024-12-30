package ec.com.sofka.usecase.transaction;

import ec.com.sofka.Account;
import ec.com.sofka.Customer;
import ec.com.sofka.Transaction;
import ec.com.sofka.gateway.AccountRepositoryGateway;
import ec.com.sofka.gateway.CustomerRepositoryGateway;
import ec.com.sofka.gateway.TransactionRepositoryGateway;
import ec.com.sofka.usecase.exception.InsufficientBalanceException;
import ec.com.sofka.usecase.exception.ResourceNotFoundException;
import ec.com.sofka.usecase.util.TransactionCost;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.function.Function;
import java.util.function.Predicate;

public class SaveTransactionUseCase {

    private final TransactionRepositoryGateway transactionRepositoryGateway;
    private final AccountRepositoryGateway accountRepositoryGateway;

    public SaveTransactionUseCase(TransactionRepositoryGateway transactionRepositoryGateway,
                                  AccountRepositoryGateway accountRepositoryGateway) {
        this.transactionRepositoryGateway = transactionRepositoryGateway;
        this.accountRepositoryGateway = accountRepositoryGateway;
    }

//    public Mono<Transaction> save(Transaction transaction) {
//        return accountRepositoryGateway.findById(transaction.getAccountId())
//                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Account not found with ID: " + transaction.getAccountId())))
//                .flatMap(account -> {
//                    if (transaction.getAmount().compareTo(BigDecimal.ZERO) < 0 && account.getBalance().add(transaction.getAmount()).compareTo(BigDecimal.ZERO) < 0) {
//                        return Mono.error(new InsufficientBalanceException("Insufficient balance to perform the transaction."));
//                    }
//                    account.setBalance(account.getBalance().add(transaction.getAmount()));
//                    return accountRepositoryGateway.save(account)
//                            .then(transactionRepositoryGateway.save(transaction));
//                });
//    }

//    public Mono<Transaction> makeBranchDeposit(Transaction transaction) {
//        return performTransaction(transaction, TransactionCost.DEPOSITO_SUCURSAL.getCosto(), true);
//    }
//
//    public Mono<Transaction> makeATMDeposit(Transaction transaction) {
//        return performTransaction(transaction, TransactionCost.DEPOSITO_CAJERO.getCosto(), true);
//    }
//
//    public Mono<Transaction> makeDepositToAnotherAccount(Transaction transaction) {
//        return performTransaction(transaction, TransactionCost.DEPOSITO_OTRA_CUENTA.getCosto(), true);
//    }
//
//    public Mono<Transaction> makePhysicalPurchase(Transaction transaction) {
//        return performTransaction(transaction, TransactionCost.COMPRA_FISICA.getCosto(), false);
//    }
//
//    public Mono<Transaction> makeOnlinePurchase(Transaction transaction) {
//        return performTransaction(transaction, TransactionCost.COMPRA_WEB.getCosto(), false);
//    }
//
//    public Mono<Transaction> makeATMWithdrawal(Transaction transaction) {
//        return performTransaction(transaction, TransactionCost.RETIRO_CAJERO.getCosto(), false);
//    }

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
                    return accountRepositoryGateway.save(account)
                            .then(transactionRepositoryGateway.save(transaction));
                });
    }
}
