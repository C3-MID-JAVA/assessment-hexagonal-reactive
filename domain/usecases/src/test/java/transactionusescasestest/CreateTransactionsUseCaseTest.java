package transactionusescasestest;

import ec.com.sofka.Account;
import ec.com.sofka.Transaction;
import ec.com.sofka.gateway.TransactionRepository;
import ec.com.sofka.transactionsusescases.CreateTransactionsUseCase;
import ec.com.sofka.transactionsusescases.GetAllTransactionsUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateTransactionsUseCaseTest {
    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private GetAllTransactionsUseCase getAllTransactionsUseCase;

    @InjectMocks
    private CreateTransactionsUseCase createTransactionsUseCase;

    @Test
    void getAllTransactions_shouldReturnTransactions() {
        // Arrange
        Account account = new Account("415e5692-b3d0-408c-b881-a8f1173d380b", BigDecimal.valueOf(1000), "Edison");
        Transaction transaction1 = new Transaction(
                "415e5692-b3d0-408c-b881-a8f1173d380b",
                "WITHDRAW_ATM",
                BigDecimal.valueOf(1000),
                BigDecimal.valueOf(2),
                account
                );
        Transaction transaction2= new Transaction(
                "415e5692-b3d0-408c-b881-a8f1173d381b",
                "DEPOSIT_BRANCH",
                BigDecimal.valueOf(1000),
                BigDecimal.valueOf(2),
                account
        );

        Flux<Transaction> transactionFlux = Flux.fromIterable(Arrays.asList(transaction1, transaction2));
        when(transactionRepository.getAllTransactions()).thenReturn(transactionFlux); // Simula el comportamiento del repositorio

        // Act
        Flux<Transaction> result = getAllTransactionsUseCase.getAllTransactions();

        // Assert
        StepVerifier.create(result)
                .expectNext(transaction1) // Verifica que la primera transacción se emite
                .expectNext(transaction2) // Verifica que la segunda transacción se emite
                .verifyComplete();

        verify(transactionRepository, times(2)).getAllTransactions(); // Verifica que el repositorio fue llamado una vez
    }

    @Test
    void apply_shouldCreateTransaction() {
        // Arrange
        Account account = new Account("415e5692-b3d0-408c-b881-a8f1173d380b", BigDecimal.valueOf(1000), "Edison");
        Transaction transaction = new Transaction(
                "415e5692-b3d0-408c-b881-a8f1173d380b",
                "WITHDRAW_ATM",
                BigDecimal.valueOf(1000),
                BigDecimal.valueOf(2),
                account
        );

        when(transactionRepository.createTransaction(transaction)).thenReturn(Mono.just(transaction)); // Mockea el comportamiento del repositorio

        // Act
        Mono<Transaction> result = createTransactionsUseCase.apply(transaction);

        // Assert
        StepVerifier.create(result)
                .expectNext(transaction) // Verifica que el resultado sea la transacción esperada
                .verifyComplete();

        verify(transactionRepository, times(1)).createTransaction(transaction); // Verifica que se haya llamado al método del repositorio
    }

}
