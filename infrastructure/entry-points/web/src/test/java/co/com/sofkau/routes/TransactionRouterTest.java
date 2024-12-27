package co.com.sofkau.routes;

import co.com.sofkau.data.AccountDTO;
import co.com.sofkau.data.CardDTO;
import co.com.sofkau.data.TransactionDTO;
import co.com.sofkau.exceptions.BodyRequestValidator;
import co.com.sofkau.exceptions.GlobalExceptionsHandler;
import co.com.sofkau.handlers.TransactionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TransactionRouterTest {

    @Mock
    private TransactionHandler transactionHandler;

    @InjectMocks
    private TransactionRouter transactionRouter;

    @Mock
    private BodyRequestValidator bodyRequestValidator;

    @Mock
    private GlobalExceptionsHandler globalExceptionsHandler;

    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        webTestClient = WebTestClient.bindToRouterFunction(transactionRouter.transactionsRoutes()).build();
    }

    @Test
    @DisplayName("Should create a new transaction and return the complete body response")
    void crateTransaction() {
        CardDTO cardDTO = new CardDTO();
        cardDTO.setCardNumber("123456789");

        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAccountNumber("123456");
        TransactionDTO transactionDTOReq = new TransactionDTO("Test Transaction", BigDecimal.valueOf(10),
                "ATM", BigDecimal.valueOf(0), accountDTO, cardDTO);
        TransactionDTO transactionDTORes = new TransactionDTO("Test Transaction",BigDecimal.valueOf(10),
                "ATM", BigDecimal.valueOf(0), accountDTO, cardDTO);

        when(transactionHandler.createTransaction(any(TransactionDTO.class))).thenReturn(Mono.just(transactionDTORes));


        webTestClient
                .post()
                .uri("/api/v1/transaction/make")
                .bodyValue(transactionDTOReq)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(TransactionDTO.class)
                .consumeWith(response -> {
                    TransactionDTO actualResponse = response.getResponseBody();
                    assert actualResponse != null;
                    assertEquals(transactionDTORes.getDescription(), actualResponse.getDescription());
                    assertEquals(transactionDTORes.getAccount().getAccountNumber(), actualResponse.getAccount().getAccountNumber());
                });

        verify(transactionHandler, times(1)).createTransaction(any(TransactionDTO.class));

    }
}