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

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GetAccountByAccountNumberUseCaseTest {

    @Mock
    IAccountRepository accountRepository;

    @InjectMocks
    GetAccountByAccountNumberUseCase getAccountByAccountNumberUseCase;


    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Should retrieve account if exist")
    void apply_success() {
        Account account = new Account();
        account.setAccountNumber("123456789");
        when(accountRepository.findByAccountNumber(account.getAccountNumber())).thenReturn(Mono.just(account));


        StepVerifier.create(getAccountByAccountNumberUseCase.apply(account.getAccountNumber()))
                .assertNext(account1 -> assertEquals(account.getAccountNumber(), account1.getAccountNumber()))
                .verifyComplete();

        verify(accountRepository, times(1)).findByAccountNumber(account.getAccountNumber());

    }

    @Test
    @DisplayName("Should NOT return an account when this dont exist")
    void apply_empty() {
        Account account = new Account();
        account.setAccountNumber("123456789");
        when(accountRepository.findByAccountNumber(account.getAccountNumber())).thenReturn(Mono.empty());


        StepVerifier.create(getAccountByAccountNumberUseCase.apply(account.getAccountNumber()))
                .verifyComplete();

        verify(accountRepository, times(1)).findByAccountNumber(account.getAccountNumber());

    }


}