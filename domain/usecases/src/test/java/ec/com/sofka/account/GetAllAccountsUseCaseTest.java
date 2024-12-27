package ec.com.sofka.account;

import ec.com.sofka.Account;
import ec.com.sofka.gateway.AccountRepository;
import ec.com.sofka.usecases.account.GetAllAccountsUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;

public class GetAllAccountsUseCaseTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private GetAllAccountsUseCase getAllAccountsUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllAccounts_success() {
        Account account1 = new Account("1", new BigDecimal("1000.0"), "123456", "John Doe");
        Account account2 = new Account("2", new BigDecimal("2000.0"), "654321", "Jane Doe");
        when(accountRepository.findAllAccounts()).thenReturn(Flux.just(account1, account2));

        Flux<Account> result = getAllAccountsUseCase.apply();

        StepVerifier.create(result)
                .expectNext(account1)
                .expectNext(account2)
                .verifyComplete();
    }

    @Test
    public void getAllAccounts_empty() {
        when(accountRepository.findAllAccounts()).thenReturn(Flux.empty());

        Flux<Account> result = getAllAccountsUseCase.apply();

        StepVerifier.create(result)
                .verifyComplete();
    }
}