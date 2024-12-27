package ec.com.sofka.transaction;

import ec.com.sofka.Account;
import ec.com.sofka.Transaction;
import ec.com.sofka.gateway.AccountRepository;
import ec.com.sofka.gateway.TransactionRepository;
import ec.com.sofka.usecases.transaction.CreateTransactionUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CreateTransactionUseCaseTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountRepository accountRepository;

    private CreateTransactionUseCase createTransactionUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        createTransactionUseCase = new CreateTransactionUseCase(transactionRepository, accountRepository);
    }

    @Test
    public void validarTransaction2_success() {
        Account account = new Account("1", new BigDecimal("1000.0"), "1234567890", "John Doe");
        Transaction transaction = new Transaction("1", new BigDecimal("100.0"), "DEBIT", new BigDecimal("1.0"), "1");

        when(accountRepository.saveAccount(any(Account.class))).thenReturn(Mono.just(account));
        when(transactionRepository.saveTransaction(any(Transaction.class))).thenReturn(Mono.just(transaction));

        Mono<Transaction> result = createTransactionUseCase.validarTransaction2(account, new BigDecimal("100.0"), "DEBIT", new BigDecimal("1.0"), true);

        StepVerifier.create(result)
                .expectNext(transaction)
                .verifyComplete();
    }

    @Test
    public void validarTransaction2_insufficientBalance() {
        Account account = new Account("1", new BigDecimal("50.0"), "1234567890", "John Doe");

        Mono<Transaction> result = createTransactionUseCase.validarTransaction2(account, new BigDecimal("100.0"), "DEBIT", new BigDecimal("1.0"), true);

        StepVerifier.create(result)
                .expectError(RuntimeException.class)
                .verify();
    }
}