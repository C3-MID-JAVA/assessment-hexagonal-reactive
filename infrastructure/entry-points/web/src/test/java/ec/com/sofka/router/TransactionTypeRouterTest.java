package ec.com.sofka.router;

import ec.com.sofka.data.TransactionTypeRequestDTO;
import ec.com.sofka.data.TransactionTypeResponseDTO;
import ec.com.sofka.exception.RequestValidationException;
import ec.com.sofka.exception.RequestValidator;
import ec.com.sofka.handler.transcationType.CreateTransactionTypeHandler;
import ec.com.sofka.handler.transcationType.GetAllTransactionTypesHandler;
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
import java.util.List;

import static org.hamcrest.Matchers.any;
import static reactor.core.publisher.Mono.when;

@ExtendWith(MockitoExtension.class)
public class TransactionTypeRouterTest {
    @Mock
    private RequestValidator requestValidator;

    @Mock
    private CreateTransactionTypeHandler createTransactionTypeHandler;

    @Mock
    private GetAllTransactionTypesHandler getAllTransactionTypesHandler;

    @InjectMocks
    private TransactionTypeRouter transactionTypeRouter;

    private WebTestClient webTestClient;

    private TransactionTypeRequestDTO transactionTypeRequest;
    private TransactionTypeResponseDTO transactionTypeResponse;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient.bindToRouterFunction(transactionTypeRouter.transactionTypesRouters()).build();

        transactionTypeRequest = new TransactionTypeRequestDTO("Deposit from branch", "Deposit from branch", new BigDecimal(10), true, false, "ACTIVE");
        transactionTypeResponse = new TransactionTypeResponseDTO("Deposit from branch", "Deposit from branch", new BigDecimal(10), true, false, "ACTIVE");

    }

    @Test
    void shouldCreateTransactionTypeSuccessfully() {
        when(requestValidator.validate(any(TransactionTypeRequestDTO.class))).thenReturn(Mono.just(transactionTypeRequest));
        when(createTransactionTypeHandler.save(transactionTypeRequest)).thenReturn(Mono.just(transactionTypeResponse));

        webTestClient.post()
                .uri("/api/transaction-types")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(transactionTypeRequest)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.type").isEqualTo(transactionTypeRequest.getType())
                .jsonPath("$.value").isEqualTo(transactionTypeRequest.getValue());
    }

    @Test
    void shouldReturnValidationErrors() {
        TransactionTypeRequestDTO invalidRequest = new TransactionTypeRequestDTO(null, "Deposit from branch", new BigDecimal(10), true, false, "ATIVE");

        when(requestValidator.validate(any(TransactionTypeRequestDTO.class)))
                .thenReturn(Mono.error(new RequestValidationException(List.of("type cannot be null", "Incorrect status"))));

        webTestClient.post()
                .uri("/api/transaction-types")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(invalidRequest)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.errors").isArray()
                .jsonPath("$.errors[0]").isEqualTo("type cannot be null")
                .jsonPath("$.errors[1]").isEqualTo("Incorrect status");
    }

    @Test
    void shouldGetAllTransactionTypesSuccessfully() {
        TransactionTypeResponseDTO transactionType1 = new TransactionTypeResponseDTO("Deposit from branch", "Deposit from branch", new BigDecimal(10), true, false, "ACTIVE");
        TransactionTypeResponseDTO transactionType2 = new TransactionTypeResponseDTO("Deposit from branch2", "Deposit from branch", new BigDecimal(10), true, false, "ACTIVE");

        when(getAllTransactionTypesHandler.getAllTransactionTypes()).thenReturn(Flux.just(transactionType1, transactionType2));

        webTestClient.get()
                .uri("/api/transaction-types")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.length()").isEqualTo(2)
                .jsonPath("$[0].type").isEqualTo(transactionType1.getType())
                .jsonPath("$[1].values").isEqualTo(transactionType2.getValue());
    }

}