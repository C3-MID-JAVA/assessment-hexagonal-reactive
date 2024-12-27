package co.com.sofka.bankingaccount.bankingaccount.domain.useCases;

import co.com.sofka.bankingaccount.bankingaccount.application.dto.TransactionDTO;
import co.com.sofka.bankingaccount.bankingaccount.application.dto.response.AccountWithTransactionsDTO;
import co.com.sofka.bankingaccount.bankingaccount.domain.factory.TransactionFactory;
import co.com.sofka.bankingaccount.bankingaccount.domain.model.Account;
import co.com.sofka.bankingaccount.bankingaccount.domain.model.Transaction;
import co.com.sofka.bankingaccount.bankingaccount.domain.useCases.gateway.AccountRepository;
import co.com.sofka.bankingaccount.bankingaccount.domain.useCases.gateway.TransactionRepository;
import co.com.sofka.bankingaccount.bankingaccount.infrastructure.drivenAdapters.mongoRepository.entities.transaction.TransactionEntity;
import co.com.sofka.bankingaccount.bankingaccount.infrastructure.persistence.mapper.TransactionMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class GetAccountWithTransactionsUseCase {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionFactory transactionFactory;
    private final TransactionMapper transactionMapper;

    public GetAccountWithTransactionsUseCase(AccountRepository accountRepository,
                                             TransactionRepository transactionRepository,
                                             TransactionFactory transactionFactory, TransactionMapper transactionMapper) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.transactionFactory = transactionFactory;
        this.transactionMapper = transactionMapper;
    }

    public Mono<AccountWithTransactionsDTO> execute(String accountId) {
        Mono<Account> cuenta = accountRepository.findById(accountId);
        Flux<Transaction> transactionFlux = transactionRepository.getTransactionsByBankAccount(accountId);

        return accountRepository.findById(accountId)
                .flatMap(account -> transactionRepository.getTransactionsByBankAccount(accountId)
                        .collectList()
                        .map(transactions -> {
                            // Usar la fábrica para reconstruir las transacciones
                            List<TransactionDTO> transactionDTOs = transactions.stream()
                                    .map(transaction -> reconstructTransaction(transactionMapper.toEntity(transaction)))
                                    .map(transaction -> new TransactionDTO(
                                            transaction.getId(),
                                            transaction.getType(),
                                            transaction.getAmount(),
                                            transaction.getCost()
                                    ))
                                    .toList();

                            return new AccountWithTransactionsDTO(
                                    account.getId(),
                                    account.getBalance(),
                                    transactions
                            );
                        }))
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Account not found: " + accountId)));
    }

    private TransactionEntity reconstructTransaction(TransactionEntity transaction) {
        return transactionFactory.createTransaction(
                transaction.getType(),
                transaction.getAmount(),
                transaction.getAccountId()
        );
    }
}
