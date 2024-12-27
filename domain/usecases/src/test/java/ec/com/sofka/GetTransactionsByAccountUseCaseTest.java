package ec.com.sofka;

import ec.com.sofka.gateway.AccountRepository;
import ec.com.sofka.gateway.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class GetTransactionsByAccountUseCaseTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountRepository accountRepository;

    private GetTransactionsByAccountUseCase useCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        useCase = new GetTransactionsByAccountUseCase(transactionRepository, accountRepository);
    }

    @Test
    void getTransactionsByAccountSuccessfully() {
        String id = "675dbabe03edcf54111957fe";
        Account account = new Account("123", BigDecimal.valueOf(1000), "John Doe", "1234567890");
        Transaction expectedTransaction = new Transaction(
                id, TransactionType.WITHDRAW_ATM, BigDecimal.valueOf(500),BigDecimal.valueOf(1.0),
                LocalDateTime.now(),"Retiro en cajero automático",account
        );

        when(transactionRepository.findByAccountNumber(account.getAccountNumber())).thenReturn(Flux.just(expectedTransaction));

        StepVerifier.create(useCase.apply(account.getAccountNumber()))
                .expectNext(expectedTransaction)
                .verifyComplete();
    }

    @Test
    void getTransactionByAccountNotFound() {
        String nonExistentAccountNumber = "nonexistent123";

        when(transactionRepository.findByAccountNumber(nonExistentAccountNumber))
                .thenReturn(Flux.empty());

        StepVerifier.create(useCase.apply(nonExistentAccountNumber))
                .expectErrorMatches(throwable ->
                        throwable instanceof RuntimeException &&
                                throwable.getMessage().equals("No transaction found with account number: " + nonExistentAccountNumber)
                )
                .verify();
    }

    @Test
    void getTransactionByAccountWithInvalidAccountNumber() {
        // Arrange
        String invalidAccountNumber = "";

        when(transactionRepository.findByAccountNumber(invalidAccountNumber))
                .thenReturn(Flux.empty());

        // Act & Assert
        StepVerifier.create(useCase.apply(invalidAccountNumber))
                .expectErrorMatches(throwable ->
                        throwable instanceof RuntimeException &&
                                throwable.getMessage().equals("No transaction found with account number: " + invalidAccountNumber)
                )
                .verify();
    }

}