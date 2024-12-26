package ec.com.sofka.accounts;

import ec.com.sofka.Account;
import ec.com.sofka.ConflictException;
import ec.com.sofka.gateway.IAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CreateAccountUseCaseTest {

        @Mock
        private IAccountRepository repository;

        private CreateAccountUseCase useCase;

        @BeforeEach
        void setUp() {
            MockitoAnnotations.openMocks(this);
            useCase = new CreateAccountUseCase(repository);
        }

        @Test
        void createAccountSuccessfully() {
            // Arrange
            Account account = new Account("675dbabe03edcf54111957fe", BigDecimal.valueOf(1000), "1234567890", "Juan Perez");
            account.setAccountNumber("123456");

            when(repository.findByAccountNumber("123456"))
                    .thenReturn(Mono.empty());

            when(repository.save(any(Account.class)))
                    .thenReturn(Mono.just(account));

            // Act & Assert
            StepVerifier.create(useCase.apply(account))
                    .expectNext(account)
                    .verifyComplete();
        }

    @Test
    void createAccountWithExistingNumberShouldFail() {
        // Arrange
        Account existingAccount = new Account("675dbabe03edcf54111957fe", BigDecimal.valueOf(1000), "1234567890", "Juan Perez");
        existingAccount.setAccountNumber("123456");

        Account newAccount = new Account("675dbabe03edcf54111957fe", BigDecimal.valueOf(1000), "1234567890", "Juan Perez");
        newAccount.setAccountNumber("123456");

        when(repository.findByAccountNumber("123456"))
                .thenReturn(Mono.just(existingAccount));

        // Act & Assert
        StepVerifier.create(useCase.apply(newAccount))
                .expectError(ConflictException.class)
                .verify();
    }

}
