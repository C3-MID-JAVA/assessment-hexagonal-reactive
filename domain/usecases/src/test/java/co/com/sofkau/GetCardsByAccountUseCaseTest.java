package co.com.sofkau;

import co.com.sofkau.gateway.ICardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetCardsByAccountUseCaseTest {

    @Mock
    private ICardRepository cardRepository;

    @Mock
    private GetAccountByAccountNumberUseCase getAccountByAccountNumberUseCase;

    @InjectMocks
    private GetCardsByAccountUseCase getCardsByAccountUseCase;

    Account account;
    Card card;
    Card card2;

    @BeforeEach
    void setUp() {
        account = new Account();
        account.setAccountNumber("123456789");
        account.setId("145263");

        card = new Card();
        card2 = new Card();

        card2.setCardNumber("123456789");
        card.setCardNumber("987654321");
    }

    @Test
    @DisplayName("Should return all card relate with account")
    void apply() {

        when(getAccountByAccountNumberUseCase.apply(account.getAccountNumber())).thenReturn(Mono.just(account));
        when(cardRepository.findByAccount_Id(account.getId())).thenReturn(Flux.just(card, card2));

        StepVerifier.create(getCardsByAccountUseCase.apply(account.getAccountNumber()))
                .expectNextCount(2)
                .verifyComplete();

        verify(cardRepository, times(1)).findByAccount_Id(account.getId());
        verify(getAccountByAccountNumberUseCase, times(1)).apply(account.getAccountNumber());
    }

    @Test
    @DisplayName("Should return zero cards")
    void apply_errorEnAccount() {

        when(getAccountByAccountNumberUseCase.apply(account.getAccountNumber())).thenReturn(Mono.empty());

        StepVerifier.create(getCardsByAccountUseCase.apply(account.getAccountNumber()))
                .expectNextCount(0)
                .verifyComplete();

        verify(cardRepository, times(0)).findByAccount_Id(account.getId());
        verify(getAccountByAccountNumberUseCase, times(1)).apply(account.getAccountNumber());
    }
}