package ec.com.sofka.cases.transaction;

import ec.com.sofka.Transaction;
import ec.com.sofka.gateway.AccountRepository;
import ec.com.sofka.gateway.TransactionRepository;
import ec.com.sofka.rules.BalanceCalculator;
import ec.com.sofka.rules.ValidateTransaction;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public class CreateTransactionUseCase {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final ValidateTransaction validateTransaction;
    private final BalanceCalculator balanceCalculator;

    public CreateTransactionUseCase(TransactionRepository transactionRepository, AccountRepository accountRepository, ValidateTransaction validateTransaction, BalanceCalculator balanceCalculator) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.validateTransaction = validateTransaction;
        this.balanceCalculator = balanceCalculator;
    }

    public Mono<Transaction> apply(Transaction transaction) {
        return Mono.just(transaction)
                .flatMap(validateTransaction::validateTransaction)
                .flatMap(this::updateBalanceAndSave);
    }

    public Mono<Transaction> updateBalanceAndSave(Transaction transaction) {
        BigDecimal newBalance = balanceCalculator.calculate(
                transaction,
                transaction.getAccount().getAvailableBalance()
        );

        transaction.getAccount().setAvailableBalance(newBalance);

        return accountRepository.save(transaction.getAccount())
                .then(transactionRepository.save(transaction));
    }
}