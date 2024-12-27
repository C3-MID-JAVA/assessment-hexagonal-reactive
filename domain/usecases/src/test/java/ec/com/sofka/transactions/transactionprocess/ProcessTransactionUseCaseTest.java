package ec.com.sofka.transactions.transactionprocess;

import ec.com.sofka.*;
import ec.com.sofka.enums.OperationType;
import ec.com.sofka.enums.TransactionType;
import ec.com.sofka.strategy.TransaccionStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

class ProcessTransactionUseCaseTest {

    private GetAccountByNumberUseCase getAccountByNumberUseCase;
    private GetTransactionStrategyUseCase getTransactionStrategyUseCase;
    private CalculateFinalBalanceUseCase calculateFinalBalanceUseCase;
    private SaveAccountUseCase saveAccountUseCase;
    private SaveTransactionUseCase saveTransactionUseCase;

    private ProcessTransactionUseCase useCase;

    @BeforeEach
    void setUp() {
        getAccountByNumberUseCase = mock(GetAccountByNumberUseCase.class);
        getTransactionStrategyUseCase = mock(GetTransactionStrategyUseCase.class);
        calculateFinalBalanceUseCase = mock(CalculateFinalBalanceUseCase.class);
        saveAccountUseCase = mock(SaveAccountUseCase.class);
        saveTransactionUseCase = mock(SaveTransactionUseCase.class);

        useCase = new ProcessTransactionUseCase(
                getAccountByNumberUseCase,
                getTransactionStrategyUseCase,
                calculateFinalBalanceUseCase,
                saveAccountUseCase,
                saveTransactionUseCase
        );
    }


    @Test
    void testProcessTransactionWithInsufficientBalance() {
        // Arrange
        Transaction transaction = new Transaction(null, BigDecimal.valueOf(200), BigDecimal.ZERO, null, TransactionType.ATM_DEPOSIT, "nonexistent");
        transaction.setType(TransactionType.ATM_WITHDRAWAL);

        Account account = new Account(BigDecimal.valueOf(1000.00),"123", "John Doe");
        TransaccionStrategy strategy = mock(TransaccionStrategy.class);
        BigDecimal transactionCost = BigDecimal.valueOf(10);
        BigDecimal finalBalance = BigDecimal.valueOf(-1010);

        when(getAccountByNumberUseCase.apply(transaction.getAccountId())).thenReturn(Mono.just(account));
        when(getTransactionStrategyUseCase.apply(account, transaction.getType(), OperationType.WITHDRAWAL, transaction.getAmount()))
                .thenReturn(Mono.just(strategy));
        when(strategy.getAmount()).thenReturn(transactionCost);
        when(calculateFinalBalanceUseCase.apply(account.getBalance(),
                transaction.getAmount(),
                transactionCost,
                OperationType.WITHDRAWAL))
                .thenReturn(finalBalance);

        // Act & Assert
        StepVerifier.create(useCase.apply(transaction, OperationType.WITHDRAWAL))
                .expectErrorMatches(throwable -> throwable instanceof ConflictException &&
                        throwable.getMessage().equals("Insufficient balance for transaction."))
                .verify();

        verify(getAccountByNumberUseCase).apply(transaction.getAccountId());
        verify(getTransactionStrategyUseCase).apply(account, transaction.getType(), OperationType.WITHDRAWAL, transaction.getAmount());
        verify(calculateFinalBalanceUseCase).apply(account.getBalance(),
                transaction.getAmount(),
                transactionCost,
                OperationType.WITHDRAWAL);
        verifyNoInteractions(saveAccountUseCase);
        verifyNoInteractions(saveTransactionUseCase);
    }
}
