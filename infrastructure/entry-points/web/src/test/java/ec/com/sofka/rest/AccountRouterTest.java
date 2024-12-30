package ec.com.sofka.rest;

import ec.com.sofka.data.AccountInDTO;
import ec.com.sofka.data.AccountOutDTO;
import ec.com.sofka.handler.AccountHandler;
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

        Mono<ServerResponse> response = accountRouter.saveAccount(serverRequest);
        ServerResponse result = response.block();

        assertNotNull(result);
        assertEquals(HttpStatus.CREATED, result.statusCode());
        assertEquals(MediaType.APPLICATION_JSON, result.headers().getContentType());

        verify(accountHandler, times(1)).saveAccount(accountInDTO);
    }

    @Test
    void shouldUpdateAccount() {
        AccountInDTO accountInDTO = new AccountInDTO("123456", BigDecimal.valueOf(1500), "cust123", "card123");
        AccountOutDTO accountOutDTO = new AccountOutDTO("1", "123456", BigDecimal.valueOf(1500), "cust123", "fass123");

        when(serverRequest.bodyToMono(AccountInDTO.class)).thenReturn(Mono.just(accountInDTO));
        when(accountHandler.saveAccount(accountInDTO)).thenReturn(Mono.just(accountOutDTO));

        Mono<ServerResponse> response = accountRouter.updateAccount(serverRequest);
        ServerResponse result = response.block();

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.statusCode());
        assertEquals(MediaType.APPLICATION_JSON, result.headers().getContentType());

        verify(accountHandler, times(1)).saveAccount(accountInDTO);
    }

    @Test
    void shouldGetAccountById() {
        String accountId = "1";
        AccountOutDTO accountOutDTO = new AccountOutDTO("1", "123456", BigDecimal.valueOf(1000), "cust123", "fass123");

        when(accountHandler.findAccountById(accountId)).thenReturn(Mono.just(accountOutDTO));
        when(serverRequest.pathVariable("id")).thenReturn(accountId);

        Mono<ServerResponse> response = accountRouter.getAccountById(serverRequest);
        ServerResponse result = response.block();

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.statusCode());
        assertEquals(MediaType.APPLICATION_JSON, result.headers().getContentType());

        verify(accountHandler, times(1)).findAccountById(accountId);
    }

    @Test
    void shouldReturnNotFoundWhenAccountNotFoundById() {
        String accountId = "non-existent-id";

        when(accountHandler.findAccountById(accountId)).thenReturn(Mono.empty());
        when(serverRequest.pathVariable("id")).thenReturn(accountId);

        Mono<ServerResponse> response = accountRouter.getAccountById(serverRequest);
        ServerResponse result = response.block();

        assertNotNull(result);
        assertEquals(HttpStatus.NOT_FOUND, result.statusCode());

        verify(accountHandler, times(1)).findAccountById(accountId);
    }

    @Test
    void shouldGetAllAccounts() {
        AccountOutDTO account1 = new AccountOutDTO("1", "123456", BigDecimal.valueOf(1000), "cust123", "fass123");
        AccountOutDTO account2 = new AccountOutDTO("2", "123457", BigDecimal.valueOf(2000), "cust124", "fass124");

        when(accountHandler.findAllAccounts()).thenReturn(Flux.just(account1, account2));

        Mono<ServerResponse> response = accountRouter.getAllAccounts(serverRequest);
        ServerResponse result = response.block();

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.statusCode());
        assertEquals(MediaType.APPLICATION_JSON, result.headers().getContentType());

        verify(accountHandler, times(1)).findAllAccounts();
    }
}
