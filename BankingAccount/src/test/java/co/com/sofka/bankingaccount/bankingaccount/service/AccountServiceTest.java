package co.com.sofka.bankingaccount.bankingaccount.service;

import co.com.sofka.bankingaccount.bankingaccount.application.dto.request.CreateAccountRequestDTO;
import co.com.sofka.bankingaccount.bankingaccount.infrastructure.drivenAdapters.mongoRepository.entities.AccountEntity;
import co.com.sofka.bankingaccount.bankingaccount.infrastructure.drivenAdapters.mongoRepository.entities.transaction.TransactionEntity;
import co.com.sofka.bankingaccount.bankingaccount.infrastructure.drivenAdapters.mongoRepository.config.IAccountRepository;
import co.com.sofka.bankingaccount.bankingaccount.infrastructure.drivenAdapters.mongoRepository.config.ITransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static reactor.core.publisher.Mono.error;

@SpringBootTest
public class AccountServiceTest {

    @Mock
    private IAccountRepository accountRepository;

    @Mock
    private ITransactionRepository transactionRepository;

    @InjectMocks
    private AccountService accountService;

    private AccountEntity mockAccount;
    private TransactionEntity mockTransaction;

    @BeforeEach
    void setUp() {
        mockAccount = new AccountEntity();
        mockAccount.setId(UUID.randomUUID().toString());
        mockAccount.setBalance(BigDecimal.valueOf(1000));
        mockAccount.setEntitledUser("Test User");

        mockTransaction = new TransactionEntity() {
            @Override
            public BigDecimal calculateImpact() {
                return BigDecimal.valueOf(-200);
            }
        };
        mockTransaction.setId(UUID.randomUUID().toString());
        mockTransaction.setAccountId(mockAccount.getId());
        mockTransaction.setAmount(BigDecimal.valueOf(-200));
        mockTransaction.setType("WITHDRAW");
    }

    @Test
    void createAccount_ShouldReturnCreatedAccount() {
        CreateAccountRequestDTO request = new CreateAccountRequestDTO();
        request.setBalance(BigDecimal.valueOf(500));
        request.setEntitledUserName("John Doe");

        when(accountRepository.save(any(AccountEntity.class))).thenReturn(Mono.just(mockAccount));

        StepVerifier.create(accountService.createAccount(request))
                .expectNextMatches(account -> account.getEntitledUser().equals("John Doe") &&
                        account.getBalance().compareTo(BigDecimal.valueOf(500)) == 0)
                .verifyComplete();

        verify(accountRepository, times(1)).save(any(AccountEntity.class));
    }

    @Test
    void getAccount_ShouldReturnAccount_WhenAccountExists() {
        when(accountRepository.findById(mockAccount.getId())).thenReturn(Mono.just(mockAccount));

        StepVerifier.create(accountService.getAccount(mockAccount.getId()))
                .expectNextMatches(account -> account.getId().equals(mockAccount.getId()))
                .verifyComplete();

        verify(accountRepository, times(1)).findById(mockAccount.getId());
    }

    @Test
    void getAccount_ShouldThrowError_WhenAccountDoesNotExist() {
        when(accountRepository.findById(anyString())).thenReturn(Mono.empty());

        StepVerifier.create(accountService.getAccount("invalid-id"))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Account not found!"))
                .verify();

        verify(accountRepository, times(1)).findById("invalid-id");
    }

    @Test
    void executeTransaction_ShouldUpdateBalance() {
        when(accountRepository.findById(mockAccount.getId())).thenReturn(Mono.just(mockAccount));
        when(transactionRepository.save(any(TransactionEntity.class))).thenReturn(Mono.just(mockTransaction));
        when(accountRepository.save(any(AccountEntity.class))).thenReturn(Mono.just(mockAccount));

        StepVerifier.create(accountService.executeTransaction(mockAccount.getId(), mockTransaction))
                .expectNextMatches(balance -> balance.compareTo(BigDecimal.valueOf(800)) == 0)
                .verifyComplete();

        verify(accountRepository, times(1)).findById(mockAccount.getId());
        verify(transactionRepository, times(1)).save(any(TransactionEntity.class));
    }
}
