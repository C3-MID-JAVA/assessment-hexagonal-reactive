package ec.com.sofka.router;

import ec.com.sofka.data.TransactionRequestDTO;
import ec.com.sofka.data.TransactionResponseDTO;
import ec.com.sofka.exception.RequestValidationException;
import ec.com.sofka.exception.RequestValidator;
import ec.com.sofka.handler.transaction.CreateTransactionHandler;
import ec.com.sofka.handler.transaction.GetAllTransactionsHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.any;
import static reactor.core.publisher.Mono.when;

@ExtendWith(MockitoExtension.class)
public class TransactionRouterTest {
    @Mock
    private RequestValidator requestValidator;

    @Mock
    private CreateTransactionHandler createTransactionHandler;

    @Mock
    private GetAllTransactionsHandler getAllTransactionsHandler;

    @InjectMocks
    private TransactionRouter transactionRouter;

    private WebTestClient webTestClient;

    private TransactionRequestDTO transactionRequest;
    private TransactionResponseDTO transactionResponse;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient.bindToRouterFunction(transactionRouter.transactionsRouters()).build();

        transactionRequest = new TransactionRequestDTO(new BigDecimal(100), new Date(), "2222222222", "transaction", "ACTIVE", "u1");
        transactionResponse = new TransactionResponseDTO("2222222222 from branch", new BigDecimal(100), new Date(), "ACTIVE",null, null);

    }

    @Test
    void shouldCreateTransactionSuccessfully() {
        when(requestValidator.validate(any(TransactionRequestDTO.class))).thenReturn(Mono.just(transactionRequest));
        when(createTransactionHandler.save(transactionRequest)).thenReturn(Mono.just(transactionResponse));

        webTestClient.post()
                .uri("/api/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(transactionRequest)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.accountNumber").isEqualTo(transactionRequest.getAccountNumber())
                .jsonPath("$.value").isEqualTo(transactionRequest.getValue());
    }

    @Test
    void shouldReturnValidationErrors() {
        TransactionRequestDTO invalidRequest = new TransactionRequestDTO(null, new Date(), "2222222222", "transaction", "ACTVE", "u1");

        when(requestValidator.validate(any(TransactionRequestDTO.class)))
                .thenReturn(Mono.error(new RequestValidationException(List.of("value cannot be null", "Incorrect status"))));

        webTestClient.post()
                .uri("/api/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(invalidRequest)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.errors").isArray()
                .jsonPath("$.errors[0]").isEqualTo("value cannot be null")
                .jsonPath("$.errors[1]").isEqualTo("Incorrect status");
    }

    @Test
    void shouldGetAllTransactionsSuccessfully() {
        TransactionResponseDTO transaction1 = new TransactionResponseDTO("2222222222 from branch", new BigDecimal(100), new Date(), "ACTIVE",null, null);
        TransactionResponseDTO transaction2 = new TransactionResponseDTO("2222222222 from branch", new BigDecimal(200), new Date(), "ACTIVE",null, null);

        when(getAllTransactionsHandler.getAllTransactions()).thenReturn(Flux.just(transaction1, transaction2));

        webTestClient.get()
                .uri("/api/transactions")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.length()").isEqualTo(2)
                .jsonPath("$[0].accountNumber").isEqualTo(transaction1.getAccountNumber())
                .jsonPath("$[1].value").isEqualTo(transaction2.getValue());
    }

}