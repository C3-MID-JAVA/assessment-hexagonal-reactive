package ec.com.sofka.UC.create;

import ec.com.sofka.Account;
import ec.com.sofka.Branch;
import ec.com.sofka.Card;
import ec.com.sofka.Transaction;
import ec.com.sofka.gateway.AccountRepository;
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
import java.util.Collections;

import static org.mockito.Mockito.*;

@SpringBootTest(classes = CreateAccountUseCaseTest.class)
public class CreateAccountUseCaseTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private CreateAccountUseCase createAccountUseCase;

    private Account account;

    @BeforeEach
    public void setUp() {
        Card card = new Card("1234-5678-9876-5432",
                "Debit", "123",
                LocalDateTime.of(2025, 12, 31, 23, 59, 59),
                1);
        Transaction transaction = new Transaction(BigDecimal.valueOf(200.50),
                new Branch(),
                LocalDateTime.now(),
                "Debit",
                null,
                1, null, "Payment for services");
        account = new Account(
                "AC12345",
                "Savings",
                BigDecimal.valueOf(1000.00),
                Collections.singletonList(card),
                1,
                Collections.singletonList(transaction)
        );
    }

    @Test
    public void testApply_shouldReturnAccount() {
        when(accountRepository.createAccount(Mockito.any(Mono.class)))
                .thenReturn(Mono.just(account));
        Mono<Account> result = createAccountUseCase.apply(Mono.just(account));
        StepVerifier.create(result)
                .expectNext(account)
                .expectComplete()
                .verify();
        Mockito.verify(accountRepository, Mockito.times(1)).createAccount(Mockito.any(Mono.class));
    }

    @Test
    void testApply_ShouldReturnError_WhenRepositoryFails() {
        when(accountRepository.createAccount(Mockito.any(Mono.class)))
                .thenReturn(Mono.error(new RuntimeException("Error saving branch")));
        Mono<Account> result = createAccountUseCase.apply(Mono.just(account));
        StepVerifier.create(result)
                .expectError(RuntimeException.class)
                .verify();
        verify(accountRepository, times(1)).createAccount(Mockito.any(Mono.class));
    }
}