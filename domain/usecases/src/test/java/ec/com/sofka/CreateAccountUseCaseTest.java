package ec.com.sofka;

import ec.com.sofka.gateway.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateAccountUseCaseTest {

    @Mock
    private AccountRepository accountRepository;
    private CreateAccountUseCase useCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        useCase = new CreateAccountUseCase(accountRepository);
    }

    @Test
    void createAccountSuccessfully() {
        Account account = new Account("6769bd6442db605146a53cf6", BigDecimal.valueOf(1000), "Jon Snow","1000001");

        when(accountRepository.findByAccountNumber("1000001"))
                .thenReturn(Mono.empty());

        when(accountRepository.save(any(Account.class)))
                .thenReturn(Mono.just(account));

        StepVerifier.create(useCase.apply(account))
                .expectNext(account)
                .verifyComplete();
    }

    @Test
    void createAccountWithExistingNumberShouldFail() {
        Account existingAccount = new Account("6769bd6442db605146a53cf6", BigDecimal.valueOf(1000), "Jon Snow", "1000001");

        Account newAccount = new Account("6769bd6442db605146a53cf6", BigDecimal.valueOf(1000), "Jon Snow", "1000001");

        when(accountRepository.findByAccountNumber("1000001"))
                .thenReturn(Mono.just(existingAccount));

        // Act & Assert
        StepVerifier.create(useCase.apply(newAccount))
                .expectError(RuntimeException.class)
                .verify();
    }
}