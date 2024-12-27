package co.com.sofka.bankingaccount.bankingaccount.domain.useCases;

import co.com.sofka.bankingaccount.bankingaccount.domain.model.Account;
import co.com.sofka.bankingaccount.bankingaccount.domain.useCases.gateway.AccountRepository;
import co.com.sofka.bankingaccount.bankingaccount.domain.useCases.gateway.TransactionRepository;
import reactor.core.publisher.Mono;


public class AccountUseCase {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public AccountUseCase(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public Mono<Account> createAccount(Account account) {
        return accountRepository.save(account);
    }

    public Mono<Account> getAccountById(String id) {
        return accountRepository.findById(id);
    }

    public Mono<Account> getAccountWithTransactions(String id) {
        return accountRepository.findById(id)
                .flatMap(account -> transactionRepository.getTransactionsByBankAccount(id)
                        .collectList()
                        .doOnNext(account::setTransactions)
                        .thenReturn(account));
    }
}
