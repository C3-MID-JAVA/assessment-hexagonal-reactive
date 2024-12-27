package ec.com.sofka;

import ec.com.sofka.data.dto.transactionDTO.TransactionRequestDTO;
import ec.com.sofka.data.dto.transactionDTO.TransactionResponseDTO;
import ec.com.sofka.handlers.transaction.GetTransactionByIdHandler;
import ec.com.sofka.handlers.transaction.SaveTransactionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@WebFluxTest(RestTransaction.class)
public class RestTransactionTest {

    @Autowired
    private WebTestClient webTestClient;

    @Mock
    private SaveTransactionHandler saveTransactionHandler;

    @Mock
    private GetTransactionByIdHandler getTransactionByIdHandler;

    private TransactionResponseDTO transactionResponseDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        transactionResponseDTO = new TransactionResponseDTO("1", new BigDecimal("100.0"), "DEBIT", new BigDecimal("1.0"), "1");
    }

    @Test
    public void getTransactionById_success() {
        when(getTransactionByIdHandler.handle(anyString())).thenReturn(Mono.just(transactionResponseDTO));

        webTestClient.get().uri("/api/transacciones/{id}", "1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(TransactionResponseDTO.class)
                .isEqualTo(transactionResponseDTO);
    }

    @Test
    public void getTransactionById_notFound() {
        when(getTransactionByIdHandler.handle(anyString())).thenReturn(Mono.empty());

        webTestClient.get().uri("/api/transacciones/{id}", "1")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(String.class)
                .isEqualTo("La transaccion con el ID 1 no existe");
    }

    @Test
    public void saveTransaction_success() {
        TransactionRequestDTO transactionRequestDTO = new TransactionRequestDTO(new BigDecimal("100.0"), "DEBIT", new BigDecimal("1.0"), "1");
        when(saveTransactionHandler.handle(any(TransactionRequestDTO.class))).thenReturn(Mono.just(transactionResponseDTO));

        webTestClient.post().uri("/api/transacciones")
                .bodyValue(transactionRequestDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(TransactionResponseDTO.class)
                .isEqualTo(transactionResponseDTO);
    }
}