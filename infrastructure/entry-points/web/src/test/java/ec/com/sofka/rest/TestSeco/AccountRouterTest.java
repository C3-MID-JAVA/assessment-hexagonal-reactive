package ec.com.sofka.rest.TestSeco;

import ec.com.sofka.data.AccountInDTO;
import ec.com.sofka.data.AccountOutDTO;
import ec.com.sofka.handler.AccountHandler;
import ec.com.sofka.rest.AccountRouter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class AccountRouterTest {

    @Mock
    private AccountHandler accountHandler;
    @InjectMocks
    private AccountRouter accountRouter;
    @Mock
    private ServerRequest serverRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateAccount() {
        AccountInDTO accountInDTO = new AccountInDTO("123456", BigDecimal.valueOf(1000), "cust123", "card123");
        AccountOutDTO accountOutDTO = new AccountOutDTO("1", "123456", BigDecimal.valueOf(1000), "cust123", "fass123");

        when(serverRequest.bodyToMono(AccountInDTO.class)).thenReturn(Mono.just(accountInDTO));
        when(accountHandler.saveAccount(accountInDTO)).thenReturn(Mono.just(accountOutDTO));

        StepVerifier.create(accountRouter.saveAccount(serverRequest))
                .expectNextMatches(response -> {
                    assertEquals(HttpStatus.CREATED, response.statusCode());
                    assertEquals(MediaType.APPLICATION_JSON, response.headers().getContentType());
                    return true;
                })
                .verifyComplete();

        verify(accountHandler).saveAccount(accountInDTO);
    }

    @Test
    void shouldUpdateAccount() {
        AccountInDTO accountInDTO = new AccountInDTO("123456", BigDecimal.valueOf(1500), "cust123", "card123");
        AccountOutDTO accountOutDTO = new AccountOutDTO("1", "123456", BigDecimal.valueOf(1500), "cust123", "fass123");

        when(serverRequest.bodyToMono(AccountInDTO.class)).thenReturn(Mono.just(accountInDTO));
        when(accountHandler.saveAccount(accountInDTO)).thenReturn(Mono.just(accountOutDTO));

        StepVerifier.create(accountRouter.updateAccount(serverRequest))
                .expectNextMatches(response -> {
                    assertEquals(HttpStatus.OK, response.statusCode());
                    assertEquals(MediaType.APPLICATION_JSON, response.headers().getContentType());
                    return true;
                })
                .verifyComplete();

        verify(accountHandler).saveAccount(accountInDTO);
    }

    @Test
    void shouldGetAccountById() {
        String accountId = "1";
        AccountOutDTO accountOutDTO = new AccountOutDTO("1", "123456", BigDecimal.valueOf(1000), "cust123", "fass123");

        when(accountHandler.findAccountById(accountId)).thenReturn(Mono.just(accountOutDTO));
        when(serverRequest.pathVariable("id")).thenReturn(accountId);

        StepVerifier.create(accountRouter.getAccountById(serverRequest))
                .expectNextMatches(response -> {
                    assertEquals(HttpStatus.OK, response.statusCode());
                    assertEquals(MediaType.APPLICATION_JSON, response.headers().getContentType());
                    return true;
                })
                .verifyComplete();

        verify(accountHandler).findAccountById(accountId);
    }

    @Test
    void shouldReturnNotFoundWhenAccountNotFoundById() {
        String accountId = "2344324";

        when(accountHandler.findAccountById(accountId)).thenReturn(Mono.empty());
        when(serverRequest.pathVariable("id")).thenReturn(accountId);

        StepVerifier.create(accountRouter.getAccountById(serverRequest))
                .expectNextMatches(response -> {
                    assertEquals(HttpStatus.NOT_FOUND, response.statusCode());
                    return true;
                })
                .verifyComplete();

        verify(accountHandler).findAccountById(accountId);
    }

    @Test
    void shouldGetAllAccounts() {
        AccountOutDTO account1 = new AccountOutDTO("1", "123456", BigDecimal.valueOf(1000), "cust123", "fass123");
        AccountOutDTO account2 = new AccountOutDTO("2", "123457", BigDecimal.valueOf(2000), "cust124", "fass124");

        when(accountHandler.findAllAccounts()).thenReturn(Flux.just(account1, account2));

        StepVerifier.create(accountRouter.getAllAccounts(serverRequest))
                .expectNextMatches(response -> {
                    assertEquals(HttpStatus.OK, response.statusCode());
                    assertEquals(MediaType.APPLICATION_JSON, response.headers().getContentType());
                    return true;
                })
                .verifyComplete();

        verify(accountHandler).findAllAccounts();
    }
}
