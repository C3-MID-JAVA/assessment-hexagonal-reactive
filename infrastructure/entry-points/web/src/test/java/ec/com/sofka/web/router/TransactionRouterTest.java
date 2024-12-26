package ec.com.sofka.web.router;

import com.fasterxml.jackson.databind.ObjectMapper;
import ec.com.sofka.Transaction;
import ec.com.sofka.TransactionType;
import ec.com.sofka.dto.TransactionRequestDTO;
import ec.com.sofka.exception.NotFoundException;
import ec.com.sofka.transaction.CreateTransactionUseCase;
import ec.com.sofka.transaction.GetAllByAccountNumberUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class TransactionRouterTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private CreateTransactionUseCase createTransactionUseCase;

    @MockitoBean
    private GetAllByAccountNumberUseCase getAllByAccountNumber;


    private TransactionRequestDTO validTransactionRequest;
    private Transaction transactionResponse;

    @BeforeEach
    void setUp(){
        validTransactionRequest = new TransactionRequestDTO(BigDecimal.valueOf(100.0), TransactionType.ATM_DEPOSIT, "123456789");
        transactionResponse = new Transaction(
                "675e0ec661737976b43cca86",
                BigDecimal.valueOf(100) ,
                BigDecimal.valueOf(2.0),
                BigDecimal.valueOf(98.0),
                TransactionType.ATM_DEPOSIT,
                LocalDateTime.now(),
                "675e0e1259d6de4eda5b29a8"
        );
    }

    @Test
    void create_validTransaction_ReturnsCreatedResponse() {
        when(createTransactionUseCase.apply(any(Transaction.class))).thenReturn(Mono.just(transactionResponse));

        webTestClient.post().uri("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(validTransactionRequest)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").isEqualTo("675e0ec661737976b43cca86")
                .jsonPath("$.fee").isEqualTo(2.0)
                .jsonPath("$.netAmount").isEqualTo(98.0)
                .jsonPath("$.type").isEqualTo("ATM_DEPOSIT");

        verify(createTransactionUseCase, times(1)).apply(any(Transaction.class));
    }

    @Test
    void create_accountNotFound_ReturnsNotFound() {
        when(createTransactionUseCase.apply(any(Transaction.class)))
                .thenReturn(Mono.error(new NotFoundException("Account not found")));

        webTestClient.post().uri("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(validTransactionRequest)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.error").isEqualTo("Account not found");

        verify(createTransactionUseCase, times(1)).apply(any(Transaction.class));
    }

    @Test
    void create_invalidTransactionData_ReturnsBadRequest() {
        TransactionRequestDTO invalidTransactionRequest = new TransactionRequestDTO(BigDecimal.valueOf(-100.0), TransactionType.ATM_DEPOSIT, "123456789");

        webTestClient.post().uri("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(invalidTransactionRequest)
                .exchange()
                .expectStatus().isBadRequest();

        verify(createTransactionUseCase, never()).apply(any(Transaction.class));
    }

    @Test
    void getAllByAccountNumber_validAccount_ReturnsTransactionList() {
        List<Transaction> transactionList = List.of(transactionResponse);

        when(getAllByAccountNumber.apply(anyString())).thenReturn(Flux.fromIterable(transactionList));

        webTestClient.get().uri("/transactions/{accountNumber}/account", "123456789")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].id").isEqualTo("675e0ec661737976b43cca86")
                .jsonPath("$[0].fee").isEqualTo(2.0)
                .jsonPath("$[0].netAmount").isEqualTo(98.0)
                .jsonPath("$[0].type").isEqualTo("ATM_DEPOSIT");

        verify(getAllByAccountNumber, times(1)).apply("123456789");
    }

    @Test
    void getAllByAccountNumber_accountNotFound_ReturnsNotFound() {
        when(getAllByAccountNumber.apply(anyString()))
                .thenReturn(Flux.error(new NotFoundException("Account not found")));

        webTestClient.get().uri("/transactions/{accountNumber}/account", "99999999")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.error").isEqualTo("Account not found");

        verify(getAllByAccountNumber, times(1)).apply("99999999");
    }
}
