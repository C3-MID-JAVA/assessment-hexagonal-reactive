package ec.com.sofka.web.router;

import ec.com.sofka.Account;
import ec.com.sofka.account.CreateAccountUseCase;
import ec.com.sofka.account.GetAccountByNumberUseCase;
import ec.com.sofka.account.GetAllByUserIdUseCase;
import ec.com.sofka.dto.AccountRequestDTO;
import ec.com.sofka.exception.NotFoundException;
import ec.com.sofka.web.TestWebConfig;
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
import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class AccountRouterTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private CreateAccountUseCase createAccountUseCase;

    @MockitoBean
    private GetAllByUserIdUseCase getAllByUserIdUseCase;

    @MockitoBean
    private GetAccountByNumberUseCase getAccountByNumberUseCase;

    private AccountRequestDTO validAccountRequest;
    private Account accountResponse;

    @BeforeEach
    void setUp() {
        validAccountRequest = new AccountRequestDTO("675e0e1259d6de4eda5b29b7");
        accountResponse = new Account("675e0e1259d6de4eda5b29a8", "12345678", BigDecimal.valueOf(0.0), "675e0e1259d6de4eda5b29b7");
    }

    @Test
    void create_validAccount_ReturnsCreatedResponse() {
        when(createAccountUseCase.apply(any(Account.class))).thenReturn(Mono.just(accountResponse));

        webTestClient.post()
                .uri("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(validAccountRequest)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.accountNumber").isEqualTo("12345678")
                .jsonPath("$.balance").isEqualTo(0.0)
                .jsonPath("$.userId").isEqualTo("675e0e1259d6de4eda5b29b7");

        verify(createAccountUseCase, times(1)).apply(any(Account.class));
    }

    @Test
    void create_DuplicateUser_ReturnsBadRequest() {
        when(createAccountUseCase.apply(any(Account.class)))
                .thenReturn(Mono.error(new NotFoundException("User not found")));

        webTestClient.post()
                .uri("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(validAccountRequest)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.error").isEqualTo("User not found");

        verify(createAccountUseCase, times(1)).apply(any(Account.class));
    }

    @Test
    void create_EmptyUserId_ReturnsBadRequest() {
        AccountRequestDTO invalidAccountRequest = new AccountRequestDTO();
        invalidAccountRequest.setUserId("");

        webTestClient.post()
                .uri("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(invalidAccountRequest)
                .exchange()
                .expectStatus().isBadRequest();

        verify(createAccountUseCase, never()).apply(any(Account.class));
    }

    @Test
    void getAllByUserId_validUser_ReturnsAccountsList() {
        List<Account> accountList = List.of(accountResponse);

        when(getAllByUserIdUseCase.apply(anyString())).thenReturn(Flux.fromIterable(accountList));

        webTestClient.get()
                .uri("/accounts/{userId}/user", "675e0e1259d6de4eda5b29b7")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].accountNumber").isEqualTo("12345678")
                .jsonPath("$[0].balance").isEqualTo(0.0)
                .jsonPath("$[0].userId").isEqualTo("675e0e1259d6de4eda5b29b7");

        verify(getAllByUserIdUseCase, times(1)).apply("675e0e1259d6de4eda5b29b7");
    }

    @Test
    void getByAccountNumber_validAccount_ReturnsAccount() {
        when(getAccountByNumberUseCase.apply(anyString())).thenReturn(Mono.just(accountResponse));

        webTestClient.get()
                .uri("/accounts/{id}", "12345678")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.accountNumber").isEqualTo("12345678")
                .jsonPath("$.balance").isEqualTo(0.0)
                .jsonPath("$.userId").isEqualTo("675e0e1259d6de4eda5b29b7");

        verify(getAccountByNumberUseCase, times(1)).apply("12345678");
    }

    @Test
    void getByAccountNumber_accountNotFound_ReturnsNotFound() {
        when(getAccountByNumberUseCase.apply(anyString()))
                .thenReturn(Mono.error(new NotFoundException("Account not found")));

        webTestClient.get()
                .uri("/accounts/{id}", "99999999")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.error").isEqualTo("Account not found");

        verify(getAccountByNumberUseCase, times(1)).apply("99999999");
    }
}
