package co.com.sofkau;

import co.com.sofkau.gateway.ICardRepository;
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
class CreateCardUseCaseTest {

    @Mock
    ICardRepository cardRepository;

    @Mock
    GetCardByCardNumberUseCase getCardByCardNumberUseCase;

    @Mock
    GetAccountByAccountNumberUseCase getAccountByAccountNumberUseCase;

    @Mock
    GetCvvCardUseCase getCvvCardUseCase;

    @InjectMocks
    CreateCardUseCase createCardUseCase;

    Card card;
    Account account;

    @BeforeEach
    void setUp() {
        card = new Card();
        card.setCardNumber("123456789");
        account = new Account();
        account.setAccountNumber("123456789");
        card.setAccount(account);
    }

    @Test
    @DisplayName("Should create card when do not exist a card with the same card number")
    void apply_createCard() {
        when(getAccountByAccountNumberUseCase.apply(account.getAccountNumber())).thenReturn(Mono.just(account));
        when(getCardByCardNumberUseCase.apply(card.getCardNumber())).thenReturn(Mono.empty());
        when(getCvvCardUseCase.apply()).thenReturn(Mono.just("1234"));
        when(cardRepository.save(card)).thenReturn(Mono.just(card));


        StepVerifier.create(createCardUseCase.apply(card))
                .assertNext(card1 -> assertEquals(card.getCardNumber(), card1.getCardNumber()))
                .verifyComplete();

        verify(getAccountByAccountNumberUseCase, times(1)).apply(account.getAccountNumber());
        verify(getCardByCardNumberUseCase, times(1)).apply(card.getCardNumber());
        verify(getCvvCardUseCase, times(1)).apply();
        verify(cardRepository, times(1)).save(card);
    }

    @Test
    @DisplayName("Should NOT create card when already exist a card with the same card number")
    void apply_errorOnCreateCard() {
        when(getCardByCardNumberUseCase.apply(card.getCardNumber())).thenReturn(Mono.just(card));



        StepVerifier.create(createCardUseCase.apply(card))
                .expectErrorMatches(err  -> err.getMessage().equals("Card already exists"))
                .verify();

        verify(getAccountByAccountNumberUseCase, times(0)).apply(account.getAccountNumber());
        verify(getCardByCardNumberUseCase, times(1)).apply(card.getCardNumber());
        verify(getCvvCardUseCase, times(0)).apply();
        verify(cardRepository, times(0)).save(card);
    }

    @Test
    @DisplayName("Should  NOT create card when account do not exist")
    void apply_errorOnAccount() {
        when(getAccountByAccountNumberUseCase.apply(account.getAccountNumber())).thenReturn(Mono.empty());
        when(getCardByCardNumberUseCase.apply(card.getCardNumber())).thenReturn(Mono.empty());


        StepVerifier.create(createCardUseCase.apply(card))
                .expectErrorMatches(err  -> err.getMessage().equals("Account does not exist"))
                .verify();

        verify(getAccountByAccountNumberUseCase, times(1)).apply(account.getAccountNumber());
        verify(getCardByCardNumberUseCase, times(1)).apply(card.getCardNumber());
        verify(getCvvCardUseCase, times(0)).apply();
        verify(cardRepository, times(0)).save(card);
    }
}