package ec.com.sofka.transaction;

import ec.com.sofka.Transaction;
import ec.com.sofka.gateway.TransactionRepository;
import ec.com.sofka.usecases.transaction.GetTransactionByIdUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class GetTransactionByIdUseCaseTest {

    @Mock
    private TransactionRepository transactionRepository;

    private GetTransactionByIdUseCase getTransactionByIdUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        getTransactionByIdUseCase = new GetTransactionByIdUseCase(transactionRepository);
    }

    @Test
    public void apply_success() {
        Transaction transaction = new Transaction("1", new BigDecimal("100.0"), "DEBIT", new BigDecimal("1.0"), "1");

        when(transactionRepository.findTransactionById(anyString())).thenReturn(Mono.just(transaction));

        Mono<Transaction> result = getTransactionByIdUseCase.apply("1");

        StepVerifier.create(result)
                .expectNext(transaction)
                .verifyComplete();
    }

    @Test
    public void apply_notFound() {
        when(transactionRepository.findTransactionById(anyString())).thenReturn(Mono.empty());

        Mono<Transaction> result = getTransactionByIdUseCase.apply("1");

        StepVerifier.create(result)
                .verifyComplete();
    }
}