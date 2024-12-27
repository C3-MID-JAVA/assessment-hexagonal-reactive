package ec.com.sofka;

import ec.com.sofka.gateway.AccountRepository;
import ec.com.sofka.gateway.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class RegisterTransactionUseCaseTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private RegisterTransactionUseCase useCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        useCase = new RegisterTransactionUseCase(transactionRepository, accountRepository);
    }

    @Test
    @DisplayName("Should perform successful transaction and subtract the correct amount and fee")
    public void testRegisterTransaction_Success() {
        Account account = new Account("123", BigDecimal.valueOf(1000), "John Doe", "1234567890");
        Transaction transaction = new Transaction(null, TransactionType.DEPOSIT_ATM, BigDecimal.valueOf(200), BigDecimal.valueOf(2.0), null, "Depósito en cajero automático", account);

        when(accountRepository.findByAccountNumber("1234567890")).thenReturn(Mono.just(account));
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));
        when(accountRepository.save(any(Account.class))).thenReturn(Mono.just(account));

        StepVerifier.create(useCase.apply(account.getAccountNumber(),transaction))
                .expectNextMatches(savedTransaction ->
                        savedTransaction.getAmount().equals(BigDecimal.valueOf(200)) &&
                                savedTransaction.getId().equals("123")
                )
                .verifyComplete();
    }

    @Test
    void insufficientBalance() {
        Account account = new Account("123", BigDecimal.valueOf(100), "John Doe", "1234567890");
        Transaction transaction = new Transaction(null, TransactionType.WITHDRAW_ATM, BigDecimal.valueOf(200), BigDecimal.valueOf(1.0), null, "Retiro en cajero automático", account);

        when(accountRepository.findByAccountNumber("1234567890")).thenReturn(Mono.just(account));

        StepVerifier.create(useCase.apply(account.getAccountNumber(),transaction))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Insufficient balance for transaction."))
                .verify();
    }

    @Test
    void accountNotFound() {
        Transaction transaction = new Transaction(null, TransactionType.DEPOSIT_ATM, BigDecimal.valueOf(200), BigDecimal.valueOf(2.0), null, "Depósito en cajero automático", null);

        when(accountRepository.findByAccountNumber(null)).thenReturn(Mono.empty());

        StepVerifier.create(useCase.apply(null,transaction))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Cuenta no encontrada con el numero de Cuenta: null"))
                .verify();
    }
}