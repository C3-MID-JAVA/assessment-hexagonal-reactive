package ec.com.sofka.account;

import ec.com.sofka.Account;
import ec.com.sofka.User;
import ec.com.sofka.exception.NotFoundException;
import ec.com.sofka.gateway.AccountRepository;
import ec.com.sofka.gateway.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateAccountUseCaseTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CreateAccountUseCase createAccountUseCase;

    @Test
    void shouldCreateAccountSuccessfully() {
        Account account = new Account("675e0e1259d6de4eda5b29b7");
        Account expectedAccount = new Account("675e0e4a59d6de4eda5b29b8", "302638f2", BigDecimal.valueOf(0.0), account.getUserId());

        User mockUser = new User();
        mockUser.setId(account.getUserId());

        when(userRepository.findById(account.getUserId())).thenReturn(Mono.just(mockUser));
        when(accountRepository.create(any(Account.class))).thenReturn(Mono.just(account));

        Mono<Account> response = createAccountUseCase.apply(account);

        StepVerifier.create(response)
                .assertNext(accountResponse -> {
                    assertNotNull(accountResponse);
                    assertEquals(account.getUserId(), accountResponse.getUserId());
                })
                .verifyComplete();

        verify(accountRepository, times(1)).create(any(Account.class));
        verify(userRepository, times(1)).findById(account.getUserId());
    }

    @Test
    void shouldThrowExceptionWhenUserNotFoundDuringAccountCreation() {
        String invalidUserId = "675e0e1259d6de4eda5b29b5";
        Account account = new Account(invalidUserId);

        when(userRepository.findById(invalidUserId)).thenReturn(Mono.empty());

        Mono<Account> response = createAccountUseCase.apply(account);

        StepVerifier.create(response)
                .expectErrorMatches(ex -> ex instanceof NotFoundException && ex.getMessage().equals("User not found"))
                .verify();

        verify(userRepository, times(1)).findById(invalidUserId);
        verify(accountRepository, never()).create(any(Account.class));
    }
}