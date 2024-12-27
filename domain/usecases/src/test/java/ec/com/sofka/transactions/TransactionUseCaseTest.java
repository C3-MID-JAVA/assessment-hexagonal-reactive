package ec.com.sofka.transactions;

import ec.com.sofka.Account;
import ec.com.sofka.ConflictException;
import ec.com.sofka.Transaction;
import ec.com.sofka.enums.OperationType;
import ec.com.sofka.enums.TransactionType;
import ec.com.sofka.gateway.IAccountRepository;
import ec.com.sofka.gateway.ITransactionRepository;
import ec.com.sofka.gateway.TransaccionStrategyFactory;
import ec.com.sofka.strategy.TransaccionStrategy;
import ec.com.sofka.strategy.TransaccionStrategyContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import static org.mockito.Mockito.*;
public class TransactionUseCaseTest {

    @Mock
    private ITransactionRepository transactionRepository;

    @Mock
    private IAccountRepository accountRepository;

    @Mock
    private TransaccionStrategyFactory strategyFactory;

    @Mock
    private TransaccionStrategy strategy;

    private TransactionUseCase useCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        useCase = new TransactionUseCase(transactionRepository, accountRepository, strategyFactory);
    }

    @Test
    void successfulDeposit() {
        // Arrange
        Account account = new Account("123", BigDecimal.valueOf(1000), "1234567890", "John Doe");
        Transaction transaction = new Transaction(null, BigDecimal.valueOf(200), BigDecimal.ZERO, null, TransactionType.ATM_DEPOSIT, "1234567890");

        when(accountRepository.findByAccountNumber("1234567890")).thenReturn(Mono.just(account));
        when(strategyFactory.getStrategy(TransactionType.ATM_DEPOSIT, OperationType.DEPOSIT)).thenReturn(strategy);
        when(strategy.getAmount()).thenReturn(BigDecimal.ZERO);
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));
        when(accountRepository.save(any(Account.class))).thenReturn(Mono.just(account));

        // Act & Assert
        StepVerifier.create(useCase.procesarTransaccion(transaction, OperationType.DEPOSIT))
                .expectNextMatches(savedTransaction ->
                        savedTransaction.getAmount().equals(BigDecimal.valueOf(200)) &&
                                savedTransaction.getAccountId().equals("123")
                )
                .verifyComplete();
    }

    @Test
    void insufficientBalance() {
        // Arrange
        Account account = new Account("123", BigDecimal.valueOf(100), "1234567890", "John Doe");
        Transaction transaction = new Transaction(null, BigDecimal.valueOf(200), BigDecimal.ZERO, null, TransactionType.ATM_WITHDRAWAL, "1234567890");

        when(accountRepository.findByAccountNumber("1234567890")).thenReturn(Mono.just(account));
        when(strategyFactory.getStrategy(TransactionType.ATM_WITHDRAWAL, OperationType.WITHDRAWAL)).thenReturn(strategy);
        when(strategy.getAmount()).thenReturn(BigDecimal.ZERO);

        // Act & Assert
        StepVerifier.create(useCase.procesarTransaccion(transaction, OperationType.WITHDRAWAL))
                .expectErrorMatches(throwable -> throwable instanceof ConflictException &&
                        throwable.getMessage().equals("Insufficient balance for transaction."))
                .verify();
    }

    @Test
    void accountNotFound() {
        // Arrange
        Transaction transaction = new Transaction(null, BigDecimal.valueOf(200), BigDecimal.ZERO, null, TransactionType.ATM_DEPOSIT, "nonexistent");

        when(accountRepository.findByAccountNumber("nonexistent")).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(useCase.procesarTransaccion(transaction, OperationType.DEPOSIT))
                .expectErrorMatches(throwable -> throwable instanceof NoSuchElementException &&
                        throwable.getMessage().equals("Cuenta no encontrada con el numero de Cuenta: nonexistent"))
                .verify();
    }

    @Test
    void strategyValidationFailed() {
        // Arrange
        Account account = new Account("123", BigDecimal.valueOf(1000), "1234567890", "John Doe");
        Transaction transaction = new Transaction(null, BigDecimal.valueOf(200), BigDecimal.ZERO, null, TransactionType.ATM_WITHDRAWAL, "1234567890");

        when(accountRepository.findByAccountNumber("1234567890")).thenReturn(Mono.just(account));
        when(strategyFactory.getStrategy(TransactionType.ATM_WITHDRAWAL, OperationType.WITHDRAWAL)).thenReturn(strategy);
        doThrow(new ConflictException("Strategy validation failed")).when(strategy).validate(account, transaction.getAmount());

        // Act & Assert
        StepVerifier.create(useCase.procesarTransaccion(transaction, OperationType.WITHDRAWAL))
                .expectErrorMatches(throwable -> throwable instanceof ConflictException &&
                        throwable.getMessage().equals("Strategy validation failed"))
                .verify();
    }
}
