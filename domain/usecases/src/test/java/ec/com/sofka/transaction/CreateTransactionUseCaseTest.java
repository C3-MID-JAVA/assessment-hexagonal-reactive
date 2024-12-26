package ec.com.sofka.transaction;

import ec.com.sofka.*;
import ec.com.sofka.exception.BadRequestException;
import ec.com.sofka.gateway.AccountRepository;
import ec.com.sofka.gateway.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateTransactionUseCaseTest {
    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionStrategyFactory factory;

    @Mock
    private ATMDepositStrategy atmDepositStrategy;

    @Mock
    private ATMWithdrawalStrategy atmWithdrawalStrategy;

    @InjectMocks
    private CreateTransactionUseCase createTransactionUseCase;

    @Test
    void shouldCreateATMDepositTransactionSuccessfully() {
        Transaction transactionRequest = new Transaction(
                BigDecimal.valueOf(100.0),
                TransactionType.ATM_DEPOSIT,
                "12345678"
        );

        Account account = new Account("675e0e4a59d6de4eda5b29b8", "12345678", BigDecimal.valueOf(500.0), "675e0e1259d6de4eda5b29b7");

        Transaction savedTransaction = new Transaction(
                "675e0ec661737976b43cca85",
                BigDecimal.valueOf(100.0),
                BigDecimal.valueOf(2.0),
                BigDecimal.valueOf(98.0),
                TransactionType.ATM_DEPOSIT,
                LocalDateTime.now(),
                "675e0e4a59d6de4eda5b29b8"
        );

        when(accountRepository.findByAccountNumber(account.getAccountNumber()))
                .thenReturn(Mono.just(account));
        when(atmDepositStrategy.calculateFee()).thenReturn(BigDecimal.valueOf(2.0));
        when(atmDepositStrategy.calculateBalance(account.getBalance(), transactionRequest.getAmount()))
                .thenReturn(BigDecimal.valueOf(598.0));
        when(factory.getStrategy(transactionRequest.getType())).thenReturn(atmDepositStrategy);
        when(transactionRepository.create(any(Transaction.class))).thenReturn(Mono.just(savedTransaction));
        when(accountRepository.create(account)).thenReturn(Mono.just(account));

        Mono<Transaction> responseMono = createTransactionUseCase.apply(transactionRequest);

        StepVerifier.create(responseMono)
                .assertNext(response -> {
                    assertNotNull(response);
                    assertEquals(BigDecimal.valueOf(98.0), response.getNetAmount());
                    assertEquals(BigDecimal.valueOf(2.0), response.getFee());
                    assertEquals(TransactionType.ATM_DEPOSIT, response.getType());
                })
                .verifyComplete();

        verify(accountRepository, times(1)).findByAccountNumber(account.getAccountNumber());
        verify(factory, times(1)).getStrategy(TransactionType.ATM_DEPOSIT);
        verify(atmDepositStrategy, times(1)).calculateFee();
        verify(atmDepositStrategy, times(1)).calculateBalance(BigDecimal.valueOf(500.0), transactionRequest.getAmount());
        verify(accountRepository, times(1)).create(account);
        verify(transactionRepository, times(1)).create(any(Transaction.class));
    }

    @Test
    void shouldCreateATMWithdrawalTransactionSuccessfully() {
        Transaction transactionRequest = new Transaction(
                BigDecimal.valueOf(50.0),
                TransactionType.ATM_WITHDRAWAL,
                "12345678"
        );

        Account account = new Account("675e0e4a59d6de4eda5b29b8", "12345678", BigDecimal.valueOf(500.0), "675e0e1259d6de4eda5b29b7");

        Transaction savedTransaction = new Transaction(
                "675e0ec661737976b43cca85",
                BigDecimal.valueOf(50.0),
                BigDecimal.valueOf(1.0),
                BigDecimal.valueOf(49.0),
                TransactionType.ATM_WITHDRAWAL,
                LocalDateTime.now(),
                "675e0e4a59d6de4eda5b29b8"
        );

        when(accountRepository.findByAccountNumber(account.getAccountNumber()))
                .thenReturn(Mono.just(account));
        when(factory.getStrategy(transactionRequest.getType())).thenReturn(atmWithdrawalStrategy);
        when(atmWithdrawalStrategy.calculateFee()).thenReturn(BigDecimal.valueOf(1.0));
        when(atmWithdrawalStrategy.calculateBalance(account.getBalance(), transactionRequest.getAmount()))
                .thenReturn(BigDecimal.valueOf(449.0));
        when(transactionRepository.create(any(Transaction.class))).thenReturn(Mono.just(savedTransaction));
        when(accountRepository.create(account)).thenReturn(Mono.just(account));

        Mono<Transaction> responseMono = createTransactionUseCase.apply(transactionRequest);

        StepVerifier.create(responseMono)
                .assertNext(response -> {
                    assertNotNull(response);
                    assertEquals(BigDecimal.valueOf(49.0), response.getNetAmount());
                    assertEquals(BigDecimal.valueOf(1.0), response.getFee());
                    assertEquals(TransactionType.ATM_WITHDRAWAL, response.getType());
                })
                .verifyComplete();

        verify(accountRepository, times(1)).findByAccountNumber(account.getAccountNumber());
        verify(factory, times(1)).getStrategy(TransactionType.ATM_WITHDRAWAL);
        verify(atmWithdrawalStrategy, times(1)).calculateFee();
        verify(atmWithdrawalStrategy, times(1)).calculateBalance(BigDecimal.valueOf(500.0), transactionRequest.getAmount());
        verify(accountRepository, times(1)).create(account);
        verify(transactionRepository, times(1)).create(any(Transaction.class));
    }

    @Test
    void shouldThrowExceptionWhenInsufficientFundsForATMWithdrawal() {
        Transaction transactionRequest = new Transaction(
                BigDecimal.valueOf(600.0),
                TransactionType.ATM_WITHDRAWAL,
                "12345678"
        );

        Account account = new Account("675e0e4a59d6de4eda5b29b8", "12345678", BigDecimal.valueOf(500.0), "675e0e1259d6de4eda5b29b7");

        when(accountRepository.findByAccountNumber(account.getAccountNumber()))
                .thenReturn(Mono.just(account));
        when(factory.getStrategy(transactionRequest.getType()))
                .thenReturn(atmWithdrawalStrategy);
        when(atmWithdrawalStrategy.calculateFee())
                .thenReturn(BigDecimal.valueOf(1.0));
        when(atmWithdrawalStrategy.calculateBalance(account.getBalance(), transactionRequest.getAmount()))
                .thenThrow(new BadRequestException("Insufficient balance for this transaction."));

        Mono<Transaction> responseMono = createTransactionUseCase.apply(transactionRequest);

        StepVerifier.create(responseMono)
                .expectErrorMatches(ex ->
                        ex instanceof BadRequestException && ex.getMessage().equals("Insufficient balance for this transaction."))
                .verify();

        verify(accountRepository, times(1)).findByAccountNumber(account.getAccountNumber());
        verify(factory, times(1)).getStrategy(TransactionType.ATM_WITHDRAWAL);
        verify(atmWithdrawalStrategy, times(1)).calculateFee();
        verify(atmWithdrawalStrategy, times(1)).calculateBalance(BigDecimal.valueOf(500.0), transactionRequest.getAmount());
        verify(accountRepository, never()).create(any(Account.class));
        verify(transactionRepository, never()).create(any(Transaction.class));
    }
}