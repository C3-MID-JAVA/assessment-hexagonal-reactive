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
class GetCvvCardUseCaseTest {

    @Mock
    ICardRepository cardRepository;

    @InjectMocks
    GetCvvCardUseCase getCvvCardUseCase;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Should generate a new cvv")
    void apply() {
        when(cardRepository.existsByCardCVV(anyString())).thenReturn(Mono.just(false));

        StepVerifier.create(getCvvCardUseCase.apply())
                .expectNextCount(1)
                .verifyComplete();

        verify( cardRepository, times(1) ).existsByCardCVV(anyString());
    }
}