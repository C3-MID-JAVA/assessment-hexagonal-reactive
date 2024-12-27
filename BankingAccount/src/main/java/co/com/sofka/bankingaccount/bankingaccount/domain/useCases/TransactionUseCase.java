package co.com.sofka.bankingaccount.bankingaccount.domain.useCases;

import co.com.sofka.bankingaccount.bankingaccount.domain.model.Transaction;
import co.com.sofka.bankingaccount.bankingaccount.domain.useCases.gateway.TransactionRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class TransactionUseCase {
    private final TransactionRepository transactionRepository;

    public TransactionUseCase(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Mono<Transaction> createTransaction(Transaction transaction) {
        return transactionRepository.createTransaction(transaction);
    }

    public Flux<Transaction> getTransactionsByBankAccount(String bankAccountId) {
        return transactionRepository.getTransactionsByBankAccount(bankAccountId);
    }
}
