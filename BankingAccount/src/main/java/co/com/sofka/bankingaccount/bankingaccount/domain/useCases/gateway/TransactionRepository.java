package co.com.sofka.bankingaccount.bankingaccount.domain.useCases.gateway;

import co.com.sofka.bankingaccount.bankingaccount.domain.model.Transaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface TransactionRepository {
    Mono<Transaction> createTransaction(Transaction transaction);
    Mono<Transaction> getTransaction(String transactionId);
    Flux<Transaction> getAllTransactions();
    Flux<Transaction> getTransactionsByBankAccount(String bankAccountId);
}
