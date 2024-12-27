package ec.com.sofka;

import ec.com.sofka.gateway.AccountRepository;
import ec.com.sofka.gateway.TransactionRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.function.Consumer;
import java.util.function.Predicate;

@Component
public class RegisterTransactionUseCase{

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;


    public RegisterTransactionUseCase(TransactionRepository transactionRepository,
                                      AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    private static final Logger logger = LoggerFactory.getLogger(RegisterTransactionUseCase.class);
    private final Consumer<Transaction> logTransaction = transaction -> logger.info("Transaction successfully registered: {}", transaction.getId());

public Mono<Transaction> apply(String accountNumber, Transaction transaction) {
        TransactionType transactionType = transaction.getTransactionType();
        BigDecimal fee = transactionType.getFee();

        return accountRepository.findByAccountNumber(accountNumber)
                .switchIfEmpty(Mono.error(new RuntimeException("Account with ID " + accountNumber + " not found")))
                .flatMap(account -> {
                    if (isTransactionTypeWithFee.test(transactionType)) {
                        BigDecimal totalAmount = transaction.getAmount().add(fee);
                        if (account.getBalance().compareTo(totalAmount) < 0) {
                            return Mono.error(new RuntimeException("Insufficient balance for transaction"));
                        }
                    }

                    Transaction newTransaction = new Transaction();
                    newTransaction.setTransactionType(transactionType);
                    newTransaction.setAmount(newTransaction.getAmount());
                    newTransaction.setFee(fee);
                    newTransaction.setDate(LocalDateTime.now());
                    newTransaction.setDescription(newTransaction.getDescription());
                    newTransaction.setAccount(account);

                    account.setBalance(account.getBalance().subtract(newTransaction.getAmount()).subtract(fee));

                    return accountRepository.save(account)
                            .then(transactionRepository.save(newTransaction))
                            .doOnNext(logTransaction);
                });
    }


    private final Predicate<TransactionType> isTransactionTypeWithFee =
            transactionType ->  transactionType == TransactionType.WITHDRAW_ATM ||
                    transactionType == TransactionType.ONLINE_PURCHASE ||
                    transactionType == TransactionType.DEPOSIT_ATM ||
                    transactionType == TransactionType.DEPOSIT_OTHER_ACCOUNT ||
                    transactionType == TransactionType.BRANCH_DEPOSIT ||
                    transactionType == TransactionType.ONSITE_CARD_PURCHASE;
}
