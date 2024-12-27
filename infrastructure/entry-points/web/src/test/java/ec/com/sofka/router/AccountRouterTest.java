package ec.com.sofka.router;

import ec.com.sofka.data.AccountRequestDTO;
import ec.com.sofka.data.AccountResponseDTO;
import ec.com.sofka.exception.RequestValidationException;
import ec.com.sofka.exception.RequestValidator;
import ec.com.sofka.handler.account.CreateAccountHandler;
import ec.com.sofka.handler.account.GetAllAccountsHandler;
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
public class AccountRouterTest {
    @Mock
    private RequestValidator requestValidator;

    @Mock
    private CreateAccountHandler createAccountHandler;

    @Mock
    private GetAllAccountsHandler getAllAccountsHandler;

    @InjectMocks
    private AccountRouter accountRouter;

    private WebTestClient webTestClient;

    private AccountRequestDTO accountRequest;
    private AccountResponseDTO accountResponse;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient.bindToRouterFunction(accountRouter.accountsRouters()).build();

        accountRequest = new AccountRequestDTO("2200000000", new BigDecimal(100), new BigDecimal(0), "ACTIVE", "u1");
        accountResponse = new AccountResponseDTO("2200000000", new BigDecimal(100), new BigDecimal(0), "ACTIVE", null);
    }

    @Test
    void shouldCreateAccountSuccessfully() {
        when(requestValidator.validate(any(AccountRequestDTO.class))).thenReturn(Mono.just(accountRequest));
        when(createAccountHandler.save(accountRequest)).thenReturn(Mono.just(accountResponse));

        webTestClient.post()
                .uri("/api/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(accountRequest)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.number").isEqualTo(accountRequest.getNumber())
                .jsonPath("$.availableBalance").isEqualTo(accountRequest.getAvailableBalance());
    }

    @Test
    void shouldReturnValidationErrors() {
        AccountRequestDTO invalidRequest = new AccountRequestDTO(null , new BigDecimal(100), new BigDecimal(0), "ACIVE", "u1");

        when(requestValidator.validate(any(AccountRequestDTO.class)))
                .thenReturn(Mono.error(new RequestValidationException(List.of("number cannot be null", "Invalid email"))));

        webTestClient.post()
                .uri("/api/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(invalidRequest)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.errors").isArray()
                .jsonPath("$.errors[0]").isEqualTo("number cannot be null")
                .jsonPath("$.errors[1]").isEqualTo("Invalid email");
    }

    @Test
    void shouldGetAllAccountsSuccessfully() {
        AccountResponseDTO account1 = new AccountResponseDTO("2200000000", new BigDecimal(100), new BigDecimal(0), "ACTIVE", null);
        AccountResponseDTO account2 = new AccountResponseDTO("2200000001", new BigDecimal(200), new BigDecimal(0), "ACTIVE", null);

        when(getAllAccountsHandler.getAllAccounts()).thenReturn(Flux.just(account1, account2));

        webTestClient.get()
                .uri("/api/accounts")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.length()").isEqualTo(2)
                .jsonPath("$[0].number").isEqualTo(account1.getNumber())
                .jsonPath("$[1].availableBalance").isEqualTo(account2.getAvailableBalance());
    }

}