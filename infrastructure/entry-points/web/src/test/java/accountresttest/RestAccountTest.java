package accountresttest;

import ec.com.sofka.Account;
import ec.com.sofka.AccountHandler;
import ec.com.sofka.RestAccount;
import ec.com.sofka.data.RequestAccountDTO;
import ec.com.sofka.data.ResponseAccountDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RestAccountTest {

    @Mock
    private AccountHandler accountHandler;

    @InjectMocks
    private RestAccount restAccount;

    private WebTestClient webTestClient;

    @Test
    void getAccountById_shouldReturnAccount() {
        // Arrange
        String accountId = "415e5692-b3d0-408c-b881-a8f1173d380b";
        Account account = new Account(accountId, BigDecimal.valueOf(1000), "Edison");
        ResponseAccountDTO responseDTO = new ResponseAccountDTO(accountId,"Edison", BigDecimal.valueOf(1000));

        when(accountHandler.getAccountById(accountId)).thenReturn(Mono.just(responseDTO));  // Mock de la respuesta

        webTestClient = WebTestClient.bindToRouterFunction(restAccount.cuentaRoutes()).build();  // Configura el WebTestClient

        // Act & Assert
        webTestClient.get().uri("/accounts/{id}", accountId)
                .exchange()
                .expectStatus().isOk()  // Verifica que la respuesta es OK
                .expectHeader().contentType("application/json")  // Verifica que el tipo de contenido sea JSON
                .expectBody(ResponseAccountDTO.class)  // Verifica que la respuesta sea un objeto ResponseAccountDTO
                .isEqualTo(responseDTO);  // Verifica que la respuesta sea igual a la esperada
    }

    @Test
    void createAccount_shouldReturnCreatedAccount() {

        // Arrange
        RequestAccountDTO requestDTO = new RequestAccountDTO(
                "Edison",
                BigDecimal.valueOf(1000)
        );

        Account account = new Account("415e5692-b3d0-408c-b881-a8f1173d380b",
                BigDecimal.valueOf(1000),
                "Edison");

        ResponseAccountDTO responseDTO = new ResponseAccountDTO(
                "415e5692-b3d0-408c-b881-a8f1173d380b", "Edison",BigDecimal.valueOf(1000)
        );

        when(accountHandler.createAccount(requestDTO)).thenReturn(Mono.just(responseDTO));  // Mock de la respuesta

        webTestClient = WebTestClient.bindToRouterFunction(restAccount.cuentaRoutes()).build();  // Configura el WebTestClient

        // Act & Assert
        webTestClient.post().uri("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestDTO)  // Envía el RequestAccountDTO como cuerpo
                .exchange()
                .expectStatus().isOk()  // Verifica que la respuesta es OK
                .expectHeader().contentType("application/json")  // Verifica que el tipo de contenido sea JSON
                .expectBody(ResponseAccountDTO.class)  // Verifica que la respuesta sea un objeto ResponseAccountDTO
                .isEqualTo(responseDTO);  // Verifica que la respuesta sea igual a la esperada
    }

}
