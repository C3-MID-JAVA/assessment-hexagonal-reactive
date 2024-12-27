package ec.com.sofka.transaction;

import ec.com.sofka.Account;
import ec.com.sofka.Transaction;
import ec.com.sofka.TransactionType;
import ec.com.sofka.exception.NotFoundException;
import ec.com.sofka.gateway.AccountRepository;
import ec.com.sofka.gateway.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetAllByAccountNumberUseCaseTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private GetAllByAccountNumberUseCase getAllByAccountNumberUseCase;

    @Test
    void shouldReturnTransactionsListForAccountNumber() {
        String accountNumber = "12345678";

        Account account = new Account("675e0e4a59d6de4eda5b29b8", "12345678", BigDecimal.valueOf(500.0), "675e0e1259d6de4eda5b29b7");

        List<Transaction> transactions = List.of(
                new Transaction(
                        "675e0ec661737976b43cca85",
                        BigDecimal.valueOf(100.0),
                        BigDecimal.valueOf(2.0),
                        BigDecimal.valueOf(98.0),
                        TransactionType.ATM_DEPOSIT,
                        LocalDateTime.now(),
                        "675e0e4a59d6de4eda5b29b8"
                ),
                new Transaction(
                        "675e0ec661737976b43cca86",
                        BigDecimal.valueOf(50.0),
                        BigDecimal.valueOf(1.0),
                        BigDecimal.valueOf(49.0),
                        TransactionType.ATM_WITHDRAWAL,
                        LocalDateTime.now(),
                        "675e0e4a59d6de4eda5b29b8"
                )
        );

        when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(Mono.just(account));
        when(transactionRepository.getAllByAccountId(account.getId())).thenReturn(Flux.fromIterable(transactions));

        Flux<Transaction> responseFlux = getAllByAccountNumberUseCase.apply(accountNumber);

        StepVerifier.create(responseFlux)
                .assertNext(response -> assertEquals("675e0ec661737976b43cca85", response.getId()))
                .assertNext(response -> assertEquals("675e0ec661737976b43cca86", response.getId()))
                .verifyComplete();

        verify(accountRepository, times(1)).findByAccountNumber(accountNumber);
        verify(transactionRepository, times(1)).getAllByAccountId(account.getId());
    }

    @Test
    void shouldThrowExceptionWhenAccountNotFoundByAccountNumber() {
        String accountNumber = "675e0e4a59d6de4eda5b29b3";

        when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(Mono.empty());

        Flux<Transaction> responseMono = getAllByAccountNumberUseCase.apply(accountNumber);

        StepVerifier.create(responseMono)
                .expectErrorMatches(ex ->
                        ex instanceof NotFoundException && ex.getMessage().equals("Account not found"))
                .verify();

        verify(accountRepository, times(1)).findByAccountNumber(accountNumber);
    }

}