package ec.com.sofka;

import ec.com.sofka.data.dto.accountDTO.AccountRequestDTO;
import ec.com.sofka.data.dto.accountDTO.AccountResponseDTO;
import ec.com.sofka.handlers.account.GetAccountByIdHandler;
import ec.com.sofka.handlers.account.GetAllAccountsHandler;
import ec.com.sofka.handlers.account.SaveAccountHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@WebFluxTest(RestAccount.class)
public class RestAccountTest {

    @Autowired
    private WebTestClient webTestClient;

    @Mock
    private GetAccountByIdHandler getAccountByIdHandler;

    @Mock
    private GetAllAccountsHandler getAllAccountsHandler;

    @Mock
    private SaveAccountHandler saveAccountHandler;

    private AccountResponseDTO accountResponseDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        accountResponseDTO = new AccountResponseDTO("1", new BigDecimal("1000.0"), "1234567890", "John Doe");
    }

    @Test
    public void getAccountById_success() {
        when(getAccountByIdHandler.getAccountById(anyString())).thenReturn(Mono.just(accountResponseDTO));

        webTestClient.get().uri("/api/cuentas/{id}", "1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(AccountResponseDTO.class)
                .isEqualTo(accountResponseDTO);
    }

    @Test
    public void getAccountById_notFound() {
        when(getAccountByIdHandler.getAccountById(anyString())).thenReturn(Mono.empty());

        webTestClient.get().uri("/api/cuentas/{id}", "1")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(String.class)
                .isEqualTo("La cuenta con el ID 1 no existe");
    }

    @Test
    public void getAllAccounts_success() {
        when(getAllAccountsHandler.getAccounts()).thenReturn(Flux.just(accountResponseDTO));

        webTestClient.get().uri("/api/cuentas")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(AccountResponseDTO.class)
                .hasSize(1)
                .contains(accountResponseDTO);
    }

    @Test
    public void createAccount_success() {
        AccountRequestDTO accountRequestDTO = new AccountRequestDTO(new BigDecimal("1000.0"), "1234567890", "John Doe");
        when(saveAccountHandler.handle(any(AccountRequestDTO.class))).thenReturn(Mono.just(accountResponseDTO));

        webTestClient.post().uri("/api/cuentas")
                .bodyValue(accountRequestDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(AccountResponseDTO.class)
                .isEqualTo(accountResponseDTO);
    }
}