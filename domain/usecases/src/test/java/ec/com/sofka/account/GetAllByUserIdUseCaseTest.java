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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetAllByUserIdUseCaseTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GetAllByUserIdUseCase getAllByUserIdUseCase;

    @Test
    void shouldReturnAccountsListForUser() {
        String userId = "675e0e1259d6de4eda5b29b6";

        List<Account> accounts = List.of(
                new Account("675e0e4a59d6de4eda5b29b8", "12345678", BigDecimal.valueOf(100.0), userId),
                new Account("675e0e4a59d6de4eda5b29b9", "87654321", BigDecimal.valueOf(200.0), userId)
        );

        User user = new User();
        user.setId("675e0e1259d6de4eda5b29b6");

        when(userRepository.findById(userId)).thenReturn(Mono.just(user));
        when(accountRepository.getAllByUserId(userId)).thenReturn(Flux.fromIterable(accounts));

        Flux<Account> response = getAllByUserIdUseCase.apply(userId);

        StepVerifier.create(response)
                .expectNextMatches(accountResponse -> "12345678".equals(accountResponse.getAccountNumber()))
                .expectNextMatches(accountResponse -> "87654321".equals(accountResponse.getAccountNumber()))
                .verifyComplete();

        verify(accountRepository, times(1)).getAllByUserId(userId);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFoundByUserId(){
        String userId = "Invalid-user";

        when(userRepository.findById(userId)).thenReturn(Mono.empty());

        Flux<Account> response = getAllByUserIdUseCase.apply(userId);

        StepVerifier.create(response)
                .expectErrorMatches(ex ->
                        ex instanceof NotFoundException && ex.getMessage().equals("User not found"))
                .verify();

        verify(userRepository, times(1)).findById(userId);
    }
}