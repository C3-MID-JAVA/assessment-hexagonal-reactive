package ec.com.sofka.routes;

import ec.com.sofka.GetAllAccountsUseCase;
import ec.com.sofka.data.AccountRequestDTO;
import ec.com.sofka.data.AccountResponseDTO;
import ec.com.sofka.handlers.AccountHandler;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.junit.jupiter.api.BeforeEach;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;

class AccountRouterTest {

    @Mock
    private AccountHandler accountHandler;

    @InjectMocks
    private AccountRouter accountRouter;

    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        webTestClient = WebTestClient.bindToRouterFunction(accountRouter.accountRoutes()).build();
    }


    @Test
    void accountRoutes() {
    }

    @Test
    void createAccount() {
        AccountRequestDTO accountRequestDTO = new AccountRequestDTO("0123456789", "Josefina", new BigDecimal("5000.00"));
        AccountResponseDTO accountResponseDTO = new AccountResponseDTO("1", new BigDecimal("5000.00"), "0123456789", "Josefina", new ArrayList<>());

        when(accountHandler.createAccount(accountRequestDTO)).thenReturn(Mono.just(accountResponseDTO));

        webTestClient.post()
                .uri("/accounts")
                .contentType(APPLICATION_JSON)
                .bodyValue(accountRequestDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.accountNumber").isEqualTo("0123456789")
                .jsonPath("$.owner").isEqualTo("Anderson Zambrano")
                .jsonPath("$.balance").isEqualTo(5000.00);
    }

    @Test
    void getAllAccounts() {
    }
}