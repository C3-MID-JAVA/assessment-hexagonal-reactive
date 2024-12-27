package accountusescasestest;

import ec.com.sofka.Account;
import ec.com.sofka.accountusescases.CreateAccountUseCase;
import ec.com.sofka.accountusescases.GetAccountByIdUseCase;
import ec.com.sofka.gateway.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateAccountUseCaseTest {
    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private CreateAccountUseCase createAccountUseCase;

    @InjectMocks
    private GetAccountByIdUseCase getAccountByIdUseCase;

    @Test
    void apply_shouldCreateAccount() {
        // Arrange
        Account account = new Account("415e5692-b3d0-408c-b881-a8f1173d380b", BigDecimal.valueOf(1000), "Edison");
        when(accountRepository.CreateAcccount(account)).thenReturn(Mono.just(account)); // Asegúrate de usar "when" aquí

        // Act
        Mono<Account> result = createAccountUseCase.apply(account);

        // Assert
        StepVerifier.create(result)
                .expectNext(account)
                .verifyComplete();

        verify(accountRepository, times(1)).CreateAcccount(account);
    }

    @Test
    void apply_shouldReturnAccount() {
        // Arrange
        String accountId = "415e5692-b3d0-408c-b881-a8f1173d380b";
        Account account = new Account(accountId, BigDecimal.valueOf(1000), "Edison");
        when(accountRepository.findByAcccountId(accountId)).thenReturn(Mono.just(account));

        // Act
        Mono<Account> result = getAccountByIdUseCase.apply(accountId);

        // Assert
        StepVerifier.create(result)
                .expectNext(account)
                .verifyComplete();

        verify(accountRepository, times(1)).findByAcccountId(accountId);  // Verifica que se haya llamado al método correctamente
    }

}
