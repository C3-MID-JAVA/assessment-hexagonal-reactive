package co.com.sofka.bankingaccount.bankingaccount.application.config;

import co.com.sofka.bankingaccount.bankingaccount.domain.factory.TransactionFactory;
import co.com.sofka.bankingaccount.bankingaccount.domain.useCases.AccountUseCase;
import co.com.sofka.bankingaccount.bankingaccount.domain.useCases.TransactionUseCase;
import co.com.sofka.bankingaccount.bankingaccount.domain.useCases.gateway.AccountRepository;
import co.com.sofka.bankingaccount.bankingaccount.domain.useCases.gateway.TransactionRepository;
import co.com.sofka.bankingaccount.bankingaccount.infrastructure.persistence.mapper.AccountMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {
    @Bean
    public AccountUseCase accountUseCase(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        return new AccountUseCase(accountRepository, transactionRepository);
    }

    @Bean
    public TransactionUseCase transactionUseCase(TransactionRepository transactionRepository,
                                                 AccountRepository accountRepository,
                                                 TransactionFactory transactionFactory) {
        return new TransactionUseCase(transactionRepository);
    }
}
