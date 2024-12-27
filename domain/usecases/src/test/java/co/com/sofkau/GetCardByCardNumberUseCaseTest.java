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
class GetCardByCardNumberUseCaseTest {

    @Mock
    ICardRepository cardRepository;

    @InjectMocks
    GetCardByCardNumberUseCase getCardByCardNumberUseCase;

    @BeforeEach
    void setUp() {

    }

    @Test
    @DisplayName("Should retrieve card if exist")
    void apply_success() {
        Card card = new Card();
        card.setCardNumber("123456789");
        when(cardRepository.findByCardNumber(card.getCardNumber())).thenReturn(Mono.just(card));


        StepVerifier.create(getCardByCardNumberUseCase.apply(card.getCardNumber()))
                .assertNext(card1 -> assertEquals(card.getCardNumber(), card1.getCardNumber()))
                .verifyComplete();

        verify(cardRepository, times(1)).findByCardNumber(card.getCardNumber());
    }

    @Test
    @DisplayName("Should NOT retrieve a card when do not exist")
    void apply_empty() {
        Card card = new Card();
        card.setCardNumber("123456789");
        when(cardRepository.findByCardNumber(card.getCardNumber())).thenReturn(Mono.empty());


        StepVerifier.create(getCardByCardNumberUseCase.apply(card.getCardNumber()))
                .verifyComplete();

        verify(cardRepository, times(1)).findByCardNumber(card.getCardNumber());
    }
}