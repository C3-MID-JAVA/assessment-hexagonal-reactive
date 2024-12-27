package ec.com.sofka.UC.create;

import ec.com.sofka.Branch;
import ec.com.sofka.Card;
import ec.com.sofka.gateway.CardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@SpringBootTest(classes = CreateCardUseCaseTest.class)
public class CreateCardUseCaseTest {
    @InjectMocks
    private CreateCardUseCase createCardUseCase;

    @Mock
    private CardRepository cardRepository;

    private Card card;

    @BeforeEach
    void setUp() {
        card = new Card("1234567890123456",
                "Debit", "123", LocalDateTime.of(2025, 12, 31, 23, 59, 59), 1);
    }

    @Test
    void testApply_shouldReturnCard() {
        when(cardRepository.createCard(Mockito.any(Mono.class)))
                .thenReturn(Mono.just(card));
        Mono<Card> result = createCardUseCase.apply(Mono.just(card));
        StepVerifier.create(result)
                .expectNext(card)
                .expectComplete()
                .verify();
        Mockito.verify(cardRepository, Mockito.times(1)).createCard(Mockito.any(Mono.class));
    }

    @Test
    void testApply_ShouldReturnError_WhenRepositoryFails() {
        when(cardRepository.createCard(Mockito.any(Mono.class)))
                .thenReturn(Mono.error(new RuntimeException("Error saving branch")));
        Mono<Card> result = createCardUseCase.apply(Mono.just(card));
        StepVerifier.create(result)
                .expectError(RuntimeException.class)
                .verify();
        verify(cardRepository, times(1)).createCard(Mockito.any(Mono.class));
    }
}

