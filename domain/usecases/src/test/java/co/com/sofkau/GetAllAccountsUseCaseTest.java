package co.com.sofkau;

import co.com.sofkau.gateway.IAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetAllAccountsUseCaseTest {

    @Mock
    IAccountRepository accountRepository;

    @InjectMocks
    GetAllAccountsUseCase getAllAccountsUseCase;


    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Should retrieve all accounts")
    void apply() {
        Account account = new Account();
        account.setAccountNumber("123456789");
        Account account2 = new Account();
        account2.setAccountNumber("987654321");

        when(accountRepository.findAllAccounts()).thenReturn(Flux.just(
                account2, account
        ));

        StepVerifier.create(getAllAccountsUseCase.apply())
                .expectNextCount(2)
                .verifyComplete();

        verify(accountRepository, times(1)).findAllAccounts();
    }

    @Test
    @DisplayName("should retrieve a empty array")
    void apply_returnEmpty() {

        when(accountRepository.findAllAccounts()).thenReturn(Flux.empty());

        StepVerifier.create(getAllAccountsUseCase.apply())
                .expectNextCount(0)
                .verifyComplete();

        verify(accountRepository, times(1)).findAllAccounts();
    }
}