package ec.com.sofka;

import ec.com.sofka.gateway.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class GetAllAccountsUseCaseTest {

    @Mock
    private AccountRepository accountRepository;

    private GetAllAccountsUseCase useCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        useCase = new GetAllAccountsUseCase(accountRepository);
    }

    @Test
    void getAllAccountsSuccessfully() {
        Account account1 = new Account(
                "6769bd6442db605146a53cf6",
                BigDecimal.valueOf(1000),
                "Jon Snow",
                "1000001"
        );
        Account account2 = new Account(
                "676e1b3f64c8d0170d42faa4",
                BigDecimal.valueOf(2000),
                "Sansa Stark",
                "1000002"
        );

        List<Account> accounts = Arrays.asList(account1, account2);

        when(accountRepository.findAll())
                .thenReturn(Flux.fromIterable(accounts));

        StepVerifier.create(useCase.apply())
                .expectNext(account1)
                .expectNext(account2)
                .verifyComplete();

    }

    @Test
    void getAllAccountsWhenEmpty() {
        when(accountRepository.findAll())
                .thenReturn(Flux.empty());

        StepVerifier.create(useCase.apply())
                .expectNextCount(0)
                .verifyComplete();
    }
}