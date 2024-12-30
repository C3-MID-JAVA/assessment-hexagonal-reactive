package ec.com.sofka.rest.TestSeco;

import ec.com.sofka.data.TransactionInDTO;
import ec.com.sofka.data.TransactionOutDTO;
import ec.com.sofka.handler.TransactionHandler;
import ec.com.sofka.rest.TransactionRouter;
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
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


public class TransactionRouterTest {
    @Mock
    private TransactionHandler transactionHandler;
    @InjectMocks
    private TransactionRouter transactionRouter;
    @Mock
    private ServerRequest serverRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldMakeBranchDeposit() {
        TransactionInDTO transactionInDTO = new TransactionInDTO("Deposito en sucursal",
                BigDecimal.valueOf(1000), "deposito", LocalDate.now(), "123456");
        TransactionOutDTO transactionOutDTO = new TransactionOutDTO("1", "Deposito en sucursal",
                BigDecimal.valueOf(1000), "deposito", LocalDate.now(), "123456");

        when(serverRequest.bodyToMono(TransactionInDTO.class)).thenReturn(Mono.just(transactionInDTO));
        when(transactionHandler.makeBranchDeposit(transactionInDTO)).thenReturn(Mono.just(transactionOutDTO));

        StepVerifier.create(transactionRouter.makeBranchDeposit(serverRequest))
                .expectNextMatches(response -> {
                    assertEquals(HttpStatus.CREATED, response.statusCode());
                    assertEquals(MediaType.APPLICATION_JSON, response.headers().getContentType());
                    return true;
                })
                .verifyComplete();

        verify(transactionHandler).makeBranchDeposit(transactionInDTO);
    }

    @Test
    void shouldMakeATMDeposit() {
        TransactionInDTO transactionInDTO = new TransactionInDTO("Deposito en cajero",
                BigDecimal.valueOf(2000), "deposito", LocalDate.now(), "123456");
        TransactionOutDTO transactionOutDTO = new TransactionOutDTO("2", "Deposito en cajero",
                BigDecimal.valueOf(2000), "deposito", LocalDate.now(), "123456");

        when(serverRequest.bodyToMono(TransactionInDTO.class)).thenReturn(Mono.just(transactionInDTO));
        when(transactionHandler.makeATMDeposit(transactionInDTO)).thenReturn(Mono.just(transactionOutDTO));

        StepVerifier.create(transactionRouter.makeATMDeposit(serverRequest))
                .expectNextMatches(response -> {
                    assertEquals(HttpStatus.CREATED, response.statusCode());
                    assertEquals(MediaType.APPLICATION_JSON, response.headers().getContentType());
                    return true;
                })
                .verifyComplete();

        verify(transactionHandler).makeATMDeposit(transactionInDTO);
    }

    @Test
    void shouldMakeDepositToAnotherAccount() {
        TransactionInDTO transactionInDTO = new TransactionInDTO("Transferencia a otra cuenta",
                BigDecimal.valueOf(500), "transferencia", LocalDate.now(), "123456");
        TransactionOutDTO transactionOutDTO = new TransactionOutDTO("3", "Transferencia a otra cuenta",
                BigDecimal.valueOf(500), "transferencia", LocalDate.now(), "123456");

        when(serverRequest.bodyToMono(TransactionInDTO.class)).thenReturn(Mono.just(transactionInDTO));
        when(transactionHandler.makeDepositToAnotherAccount(transactionInDTO)).thenReturn(Mono.just(transactionOutDTO));

        StepVerifier.create(transactionRouter.makeDepositToAnotherAccount(serverRequest))
                .expectNextMatches(response -> {
                    assertEquals(HttpStatus.CREATED, response.statusCode());
                    assertEquals(MediaType.APPLICATION_JSON, response.headers().getContentType());
                    return true;
                })
                .verifyComplete();

        verify(transactionHandler).makeDepositToAnotherAccount(transactionInDTO);
    }
}
