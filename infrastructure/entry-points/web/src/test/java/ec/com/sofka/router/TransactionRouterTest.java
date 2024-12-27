package ec.com.sofka.router;

import ec.com.sofka.data.TransactionRequestDTO;
import ec.com.sofka.data.TransactionResponseDTO;
import ec.com.sofka.enums.TransactionType;
import ec.com.sofka.globalexceptions.GlobalErrorHandler;
import ec.com.sofka.handler.TransactionHandler;
import ec.com.sofka.service.ValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;

class TransactionRouterTest {

    private WebTestClient webTestClient;

    @Mock
    private TransactionHandler transactionHandler;

    @Mock
    private ValidationService validationService;

    @Mock
    private GlobalErrorHandler globalErrorHandler;

    @InjectMocks
    private TransactionRouter transactionRouter;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient.bindToRouterFunction(transactionRouter.transactionRoutes()).build();
    }

    @Test
    void testCreateDepositSuccess() {
        // Arrange
        TransactionRequestDTO depositRequest = new TransactionRequestDTO("0123456789", new BigDecimal("500.75"), TransactionType.BRANCH_DEPOSIT);
        TransactionResponseDTO depositResponse = new TransactionResponseDTO(TransactionType.BRANCH_DEPOSIT, new BigDecimal("500.75"), "0123456789");

        when(validationService.validate(depositRequest, TransactionRequestDTO.class)).thenReturn(Mono.just(depositRequest));
        when(transactionHandler.createDeposit(depositRequest)).thenReturn(Mono.just(depositResponse));

        // Act & Assert
        webTestClient.post()
                .uri("/transactions/deposit")
                .contentType(APPLICATION_JSON)
                .bodyValue(depositRequest)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(TransactionResponseDTO.class)
                .value(response -> {
                    assert response.getTransactionId().equals("12345");
                    assert response.getAmount().equals(new BigDecimal("500.75"));
                });
    }

    @Test
    void testCreateWithdrawSuccess() {
        // Arrange
        TransactionRequestDTO withdrawalRequest = new TransactionRequestDTO("0123456789", new BigDecimal("-50.00"), TransactionType.BRANCH_WITHDRAWAL);
        TransactionResponseDTO withdrawalResponse = new TransactionResponseDTO("12345", new BigDecimal("-50.00"), "WITHDRAWAL");

        when(validationService.validate(withdrawalRequest, TransactionRequestDTO.class)).thenReturn(Mono.just(withdrawalRequest));
        when(transactionHandler.createWithDrawal(withdrawalRequest)).thenReturn(Mono.just(withdrawalResponse));

        // Act & Assert
        webTestClient.post()
                .uri("/transactions/withdrawal")
                .contentType(APPLICATION_JSON)

