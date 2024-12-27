package ec.com.sofka.account;
import ec.com.sofka.Account;
import ec.com.sofka.gateway.AccountRepository;
import ec.com.sofka.usecases.account.CreateAccountUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CreateAccountUseCaseTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private CreateAccountUseCase createAccountUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createAccount_success() {
        Account account = new Account("1", new BigDecimal("1000.0"), "123456", "John Doe");
        when(accountRepository.saveAccount(any(Account.class))).thenReturn(Mono.just(account));

        Mono<Account> result = createAccountUseCase.apply(account);

        StepVerifier.create(result)
                .expectNext(account)
                .verifyComplete();
    }

    @Test
    public void createAccount_failure() {
        Account account = new Account("1", new BigDecimal("1000.0"), "123456", "John Doe");
        when(accountRepository.saveAccount(any(Account.class))).thenReturn(Mono.empty());

        Mono<Account> result = createAccountUseCase.apply(account);

        StepVerifier.create(result)
                .expectError(RuntimeException.class)
                .verify();
    }
}