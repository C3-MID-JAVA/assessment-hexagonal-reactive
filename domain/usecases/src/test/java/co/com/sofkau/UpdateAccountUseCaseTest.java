package co.com.sofkau;

import co.com.sofkau.gateway.IAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateAccountUseCaseTest {

    @Mock
    private IAccountRepository accountRepository;

    @InjectMocks
    private UpdateAccountUseCase updateAccountUseCase;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Should save an account modified")
    void apply() {
        Account account = new Account();
        account.setAccountNumber("123456789");

        when(accountRepository.save(account)).thenReturn(Mono.just(account));

        StepVerifier.create(updateAccountUseCase.apply(account))
                .expectNext(account)
                .verifyComplete();

        verify(accountRepository, times(1)).save(account);
    }
}