package transactionresttest;

import ec.com.sofka.Account;
import ec.com.sofka.RestTransactions;
import ec.com.sofka.TransactionHandler;
import ec.com.sofka.data.RequestTransactionDTO;
import ec.com.sofka.data.ResponseTransactionDTO;
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
import java.time.LocalDateTime;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RestTransactionsTest {
    @Mock
    private TransactionHandler transactionHandler;

    @InjectMocks
    private RestTransactions restTransactions;

    private WebTestClient webTestClient;

    @Test
    void getAllTransactions_shouldReturnTransactions() {

        // Arrange
        ResponseTransactionDTO responseDTO = new ResponseTransactionDTO(
                "415e5692-b3d0-408c-b881-a8f1173d380b",
                "DEPOSIT_BRANCH",
                BigDecimal.valueOf(1000.00),
                BigDecimal.valueOf(1),
                new Account("415e5692-b3d0-408c-b881-a8f1173d380b", BigDecimal.valueOf(1000), "Edison"),
                LocalDateTime.now()
        );

        when(transactionHandler.getAllTransactions()).thenReturn(Flux.just(responseDTO));  // Mock de la respuesta

        webTestClient = WebTestClient.bindToRouterFunction(restTransactions.transactionRoutes()).build();  // Configura el WebTestClient

        // Act & Assert
        webTestClient.get().uri("/transactions")
                .exchange()
                .expectStatus().isOk()  // Verifica que la respuesta es OK
                .expectHeader().contentType("application/json")  // Verifica que el tipo de contenido sea JSON
                .expectBodyList(ResponseTransactionDTO.class)  // Verifica que la respuesta sea una lista de transacciones
                .hasSize(1); // Verifica que la lista tenga 1 elemento
    }


    @Test
    void createTransaction_shouldReturnCreatedTransaction() {
        // Arrange

        Account account = new Account("415e5692-b3d0-408c-b881-a8f1173d380b", BigDecimal.valueOf(1000), "Edison");

        RequestTransactionDTO requestDTO = new RequestTransactionDTO(
                "DEPOSIT_BRANCH",
                BigDecimal.valueOf(1000.00),
                BigDecimal.valueOf(1),
                "675e962784c89c424d3bc7b6");

        ResponseTransactionDTO responseDTO = new ResponseTransactionDTO(
                "415e5692-b3d0-408c-b881-a8f1173d380b",
                "DEPOSIT_BRANCH",
                BigDecimal.valueOf(1000.00),
                BigDecimal.valueOf(1),
                account,
                LocalDateTime.now()
                );

        when(transactionHandler.createTransaction(requestDTO)).thenReturn(Mono.just(responseDTO));  // Mock de la respuesta

        webTestClient = WebTestClient.bindToRouterFunction(restTransactions.transactionRoutes()).build();  // Configura el WebTestClient

        // Act & Assert
        webTestClient.post().uri("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestDTO)  // Envía el RequestTransactionDTO como cuerpo
                .exchange()
                .expectStatus().isOk()  // Verifica que la respuesta es OK
                .expectHeader().contentType("application/json")  // Verifica que el tipo de contenido sea JSON
                .expectBody(ResponseTransactionDTO.class)  // Verifica que la respuesta sea un objeto ResponseTransactionDTO
                .isEqualTo(responseDTO);  // Verifica que la respuesta sea igual a la esperada
    }

}
