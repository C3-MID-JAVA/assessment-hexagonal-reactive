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
class CreateAccountUseCaseTest {

    @Mock
    IAccountRepository accountRepository;

    @Mock
    GetAccountByAccountNumberUseCase getAccountByAccountNumberUseCase;

    @InjectMocks
    CreateAccountUseCase createAccountUseCase;

    Account testAccount;
    Account testAccountReturned;

    @BeforeEach
    void setUp() {
        testAccount = new Account();
        testAccount.setAccountNumber("123456789");
        testAccount.setOwnerName("John");

        testAccountReturned = new Account();
        testAccountReturned.setAccountNumber("123456789");
        testAccountReturned.setOwnerName("John DOS");
    }

    @Test
    @DisplayName("Should create Account when there is not account with the same accountNumber")
    void apply_Success() {

        when(getAccountByAccountNumberUseCase.apply(testAccount.getAccountNumber())).thenReturn(Mono.empty());
        when(accountRepository.save(testAccount)).thenReturn(Mono.just(testAccount));


        StepVerifier.create(createAccountUseCase.apply(testAccount))
                .assertNext(account -> assertEquals(testAccount.getAccountNumber(), account.getAccountNumber()) )
                .verifyComplete();


        verify(getAccountByAccountNumberUseCase, times(1)).apply("123456789");
        verify(accountRepository, times(1)).save(testAccount);

    }

    @Test
    @DisplayName("Should NOT create account when already exist an account with the same account number")
    void apply_Error() {

        when(getAccountByAccountNumberUseCase.apply(testAccount.getAccountNumber())).thenReturn(Mono.just(testAccountReturned));


        StepVerifier.create(createAccountUseCase.apply(testAccount))
                .expectErrorMatches(err  -> err.getMessage().equals("Account already exists"))
                .verify();


        verify(getAccountByAccountNumberUseCase, times(1)).apply("123456789");
        verify(accountRepository, times(0)).save(testAccount);

    }
}