package ec.com.sofka.account;

import ec.com.sofka.Account;
import ec.com.sofka.gateway.AccountRepository;
import ec.com.sofka.usecases.account.GetAccountByIdUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class GetAccountByIdUseCaseTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private GetAccountByIdUseCase getAccountByIdUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAccountById_success() {
        Account account = new Account("1", new BigDecimal("1000.0"), "123456", "John Doe");
        when(accountRepository.findByAccountById(anyString())).thenReturn(Mono.just(account));

        Mono<Account> result = getAccountByIdUseCase.apply("1");

        StepVerifier.create(result)
                .expectNext(account)
                .verifyComplete();
    }

    @Test
    public void getAccountById_notFound() {
        when(accountRepository.findByAccountById(anyString())).thenReturn(Mono.empty());

        Mono<Account> result = getAccountByIdUseCase.apply("1");

        StepVerifier.create(result)
                .verifyComplete();
    }
}