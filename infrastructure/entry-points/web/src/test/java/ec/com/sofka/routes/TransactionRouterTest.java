package ec.com.sofka.routes;

import ec.com.sofka.TransactionType;
import ec.com.sofka.data.TransactionRequestDTO;
import ec.com.sofka.data.TransactionResponseDTO;
import ec.com.sofka.exceptions.GlobalExceptionHandler;
import ec.com.sofka.handlers.TransactionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TransactionRouterTest {

    @Autowired
    private WebTestClient webTestClient;

    @Mock
    private TransactionHandler transactionHandler;
    private GlobalExceptionHandler globalExceptionHandler;

    private RouterFunction<?> routerFunction;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        TransactionRouter transactionRouter = new TransactionRouter(transactionHandler, globalExceptionHandler);
        this.routerFunction = transactionRouter.transactionRoutes();
        this.webTestClient = WebTestClient.bindToRouterFunction(routerFunction).build();
    }

    @Test
    public void testCreateDepositSuccess() {
        TransactionRequestDTO requestDTO = new TransactionRequestDTO(BigDecimal.valueOf(200), TransactionType.WITHDRAW_ATM, "123", "Retiro en cajero");
        TransactionResponseDTO responseDTO = new TransactionResponseDTO("0001", null, BigDecimal.valueOf(1.0), BigDecimal.valueOf(200), TransactionType.WITHDRAW_ATM, LocalDateTime.now(), "Retiro en cajero");
        when(transactionHandler.registerTransaction(any())).thenReturn(Mono.just(responseDTO));

        webTestClient.post()
                .uri("/transactions/deposit")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(TransactionResponseDTO.class);

        verify(transactionHandler, times(1)).registerTransaction(any());
    }



    @Test
    public void testGetTransactionByIdSuccess() {
        String transactionAccountNumber = "123";
        TransactionResponseDTO responseDTO = new TransactionResponseDTO("0001", null, BigDecimal.valueOf(1.0), BigDecimal.valueOf(200), TransactionType.WITHDRAW_ATM, LocalDateTime.now(), "Retiro en cajero");
        when(transactionHandler.getTransactionsByAccount(transactionAccountNumber)).thenReturn(Flux.just(responseDTO));

        webTestClient.get()
                .uri("/transactions/{transactionId}", transactionAccountNumber)
                .exchange()
                .expectStatus().isOk()
                .expectBody(TransactionResponseDTO.class);

        verify(transactionHandler, times(1)).getTransactionsByAccount(transactionAccountNumber);
    }
}
