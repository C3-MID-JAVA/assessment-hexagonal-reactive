package ec.com.sofka.account;

import ec.com.sofka.Account;
import ec.com.sofka.exception.NotFoundException;
import ec.com.sofka.gateway.AccountRepository;
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
class GetAccountByNumberUseCaseTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private GetAccountByNumberUseCase getAccountByNumberUseCase;

    @Test
    void shouldFindAccountByAccountNumberSuccessfully() {
        String accountNumber = "12345678";
        Account account = new Account(
                "675e0e4a59d6de4eda5b29b8",
                accountNumber,
                BigDecimal.valueOf(100.0),
                "675e0e1259d6de4eda5b29b7");

        when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(Mono.just(account));

        Mono<Account> response = getAccountByNumberUseCase.apply(accountNumber);

        StepVerifier.create(response)
                .assertNext(accountResponse -> {
                    assertNotNull(accountResponse);
                    assertEquals(accountNumber, accountResponse.getAccountNumber());
                })
                .verifyComplete();

        verify(accountRepository, times(1)).findByAccountNumber(accountNumber);
    }

    @Test
    void shouldThrowExceptionWhenAccountNotFoundByAccountNumber() {
        String accountNumber = "675e0e4a59d6de4eda5b29b3";

        when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(Mono.empty());

        Mono<Account> response = getAccountByNumberUseCase.apply(accountNumber);

        StepVerifier.create(response)
                .expectErrorMatches(ex -> ex instanceof NotFoundException && ex.getMessage().equals("Account not found"))
                .verify();

        verify(accountRepository, times(1)).findByAccountNumber(accountNumber);
    }
}