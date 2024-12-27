package co.com.sofkau.routes;

import co.com.sofkau.ErrorDetails;
import co.com.sofkau.data.AccountDTO;
import co.com.sofkau.data.AccountSimpleRequestDTO;
import co.com.sofkau.data.CardDTO;
import co.com.sofkau.exceptions.BodyRequestValidator;
import co.com.sofkau.exceptions.GlobalExceptionsHandler;
import co.com.sofkau.handlers.CardHandler;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CardRouterTest {

    @Mock
    private CardHandler cardHandler;

    @Mock
    private BodyRequestValidator bodyRequestValidator;

    @Mock
    private GlobalExceptionsHandler globalExceptionsHandler;

    @InjectMocks
    CardRouter cardRouter;

    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        webTestClient = WebTestClient.bindToRouterFunction(cardRouter.cardRoutes()).build();
    }



    @Test
    @DisplayName("Should call create card endpoint successfully")
    void createCard() {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAccountNumber("123456");
        CardDTO cardDTORequest = new CardDTO("CARD TEST", "123456789",
                "TDEBIT","ACTIVE", "12-12-2024",
                BigDecimal.valueOf(1000), "TEST HOLDER",
                accountDTO
        );
        CardDTO cardDTOResponse = new CardDTO("CARD TEST", "123456789",
                "TDEBIT","ACTIVE", "12-12-2024",
                BigDecimal.valueOf(1000), "TEST HOLDER",
                accountDTO
        );


        when(cardHandler.createCard(any(CardDTO.class))).thenReturn(Mono.just(cardDTOResponse));


        webTestClient
                .post()
                .uri("/api/v1/card/create")
                .bodyValue(cardDTORequest)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(CardDTO.class)
                .consumeWith(response -> {
                    CardDTO actualResponse = response.getResponseBody();
                    assert actualResponse != null;
                    assertEquals(cardDTORequest.getCardNumber(), actualResponse.getCardNumber());
                    assertEquals(cardDTORequest.getAccount().getAccountNumber(), actualResponse.getAccount().getAccountNumber());
                });

        verify(cardHandler, times(1)).createCard(any(CardDTO.class));
    }


    @Test
    @DisplayName("Should retrieve all cards by accountNumber when exists")
    void getCardsByAccount() {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAccountNumber("123456");
        CardDTO cardDTO = new CardDTO("CARD TEST", "123456789",
                "TDEBIT","ACTIVE", "12-12-2024",
                BigDecimal.valueOf(1000), "TEST HOLDER",
                accountDTO
        );
        CardDTO cardDTO2 = new CardDTO("CARD TEST", "123456789",
                "TDEBIT","ACTIVE", "12-12-2024",
                BigDecimal.valueOf(1000), "TEST HOLDER",
                accountDTO
        );
        AccountSimpleRequestDTO accountDTO2 = new AccountSimpleRequestDTO("123456789");
        accountDTO2.setAccountNumber("123456");


        when(cardHandler.getCardsByAccountNumber(anyString())).thenReturn(Flux.just(cardDTO, cardDTO2));


        webTestClient
                .post()
                .uri("/api/v1/card/byAccount")
                .bodyValue(accountDTO2)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(CardDTO.class)
                .consumeWith(response -> {
                    List<CardDTO> actualResponse = response.getResponseBody();
                    assert actualResponse != null;
                    assertEquals(cardDTO.getCardNumber(), actualResponse.get(0).getCardNumber());
                    assertEquals(cardDTO2.getCardNumber(), actualResponse.get(1).getCardNumber());
                });

        verify(cardHandler, times(1)).getCardsByAccountNumber(anyString());

    }
}