package ec.com.sofka.UC.create;

import ec.com.sofka.Account;
import ec.com.sofka.Branch;
import ec.com.sofka.Transaction;
import ec.com.sofka.gateway.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

@SpringBootTest(classes = CreateTransactionUseCaseTest.class)
public class CreateTransactionUseCaseTest {
    @InjectMocks
    private CreateTransactionUseCase createTransactionUseCase;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private Transaction transaction;

    @BeforeEach
    void setUp() {
        when(transaction.getId()).thenReturn(1);
        when(transaction.getAmount()).thenReturn(new BigDecimal("100.00"));
        when(transaction.getDate()).thenReturn(LocalDateTime.now());
        when(transaction.getDescription()).thenReturn("test");
        when(transaction.getType()).thenReturn("test");
        when(transaction.getDestinationAccount()).thenReturn(new Account());
        when(transaction.getSourceAccount()).thenReturn(new Account());
        when(transaction.getBranch()).thenReturn(new Branch());
    }

    @Test
    void testApply_shouldReturnCustomer() {
        when(transactionRepository.createTransaction(Mockito.any(Mono.class)))
                .thenReturn(Mono.just(transaction));
        Mono<Transaction> result = createTransactionUseCase.apply(Mono.just(transaction));
        StepVerifier.create(result)
                .expectNext(transaction)
                .expectComplete()
                .verify();
        Mockito.verify(transactionRepository, Mockito.times(1)).createTransaction(Mockito.any(Mono.class));
    }

    @Test
    void testApply_ShouldReturnError_WhenRepositoryFails() {
        when(transactionRepository.createTransaction(Mockito.any(Mono.class)))
                .thenReturn(Mono.error(new RuntimeException("Error saving branch")));
        Mono<Transaction> result = createTransactionUseCase.apply(Mono.just(transaction));
        StepVerifier.create(result)
                .expectError(RuntimeException.class)
                .verify();
        verify(transactionRepository, times(1)).createTransaction(Mockito.any(Mono.class));
    }
}
