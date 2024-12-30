package ec.com.sofka.rest.TestSeco;

import ec.com.sofka.data.CustomerInDTO;
import ec.com.sofka.data.CustomerOutDTO;
import ec.com.sofka.handler.CustomerHandler;
import ec.com.sofka.rest.CustomerRouter;
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

import java.time.LocalDate;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class CustomerRouterTest {
    @Mock
    private CustomerHandler customerHandler;
    @InjectMocks
    private CustomerRouter customerRouter;
    @Mock
    private ServerRequest serverRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateCustomer() {
        CustomerInDTO customerInDTO = new CustomerInDTO("ID123", "John", "Doe", "john.doe@example.com",
                "1234567890", "123 Main St", LocalDate.of(1985, 5, 15));
        CustomerOutDTO customerOutDTO = new CustomerOutDTO("12345", "ID123", "John", "Doe",
                "john.doe@example.com", "1234567890", "123 Main St", LocalDate.of(1985, 5, 15));

        when(serverRequest.bodyToMono(CustomerInDTO.class)).thenReturn(Mono.just(customerInDTO));
        when(customerHandler.saveCustomer(customerInDTO)).thenReturn(Mono.just(customerOutDTO));

        StepVerifier.create(customerRouter.saveCustomer(serverRequest))
                .expectNextMatches(response -> {
                    assertEquals(HttpStatus.CREATED, response.statusCode());
                    assertEquals(MediaType.APPLICATION_JSON, response.headers().getContentType());
                    return true;
                })
                .verifyComplete();

        verify(customerHandler).saveCustomer(customerInDTO);
        verify(serverRequest).bodyToMono(CustomerInDTO.class);
    }

    @Test
    void shouldGetCustomerById() {
        String customerId = "12345";
        CustomerOutDTO customerOutDTO = new CustomerOutDTO("12345", "ID123", "John", "Doe",
                "john.doe@example.com", "1234567890", "123 Main St", LocalDate.of(1985, 5, 15));

        when(customerHandler.findCustomerById(customerId)).thenReturn(Mono.just(customerOutDTO));
        when(serverRequest.pathVariable("id")).thenReturn(customerId);

        StepVerifier.create(customerRouter.getCustomerById(serverRequest))
                .expectNextMatches(response -> {
                    assertEquals(HttpStatus.OK, response.statusCode());
                    assertEquals(MediaType.APPLICATION_JSON, response.headers().getContentType());
                    return true;
                })
                .verifyComplete();

        verify(customerHandler).findCustomerById(customerId);
    }

    @Test
    void shouldReturnNotFoundWhenCustomerNotFoundById() {
        String customerId = "23123213123";

        when(customerHandler.findCustomerById(customerId)).thenReturn(Mono.empty());
        when(serverRequest.pathVariable("id")).thenReturn(customerId);

        StepVerifier.create(customerRouter.getCustomerById(serverRequest))
                .expectNextMatches(response -> {
                    assertEquals(HttpStatus.NOT_FOUND, response.statusCode());
                    return true;
                })
                .verifyComplete();

        verify(customerHandler).findCustomerById(customerId);
    }

    @Test
    void shouldGetAllCustomers() {
        CustomerOutDTO customer1 = new CustomerOutDTO("12345", "ID123", "John", "Doe",
                "john.doe@example.com", "1234567890", "123 Main St", LocalDate.of(1985, 5, 15));
        CustomerOutDTO customer2 = new CustomerOutDTO("12346", "ID124", "Jane", "Doe",
                "jane.doe@example.com", "0987654321", "456 Elm St", LocalDate.of(1990, 6, 25));

        when(customerHandler.findAllCustomers()).thenReturn(Flux.just(customer1, customer2));

        StepVerifier.create(customerRouter.getAllCustomers(serverRequest))
                .expectNextMatches(response -> {
                    assertEquals(HttpStatus.OK, response.statusCode());
                    assertEquals(MediaType.APPLICATION_JSON, response.headers().getContentType());
                    return true;
                })
                .verifyComplete();

        verify(customerHandler).findAllCustomers();
    }

    @Test
    void shouldDeleteCustomer() {
        String customerId = "12345";

        when(customerHandler.deleteCustomerById(customerId)).thenReturn(Mono.empty());
        when(serverRequest.pathVariable("id")).thenReturn(customerId);

        StepVerifier.create(customerRouter.deleteCustomer(serverRequest))
                .expectNextMatches(response -> {
                    assertEquals(HttpStatus.NO_CONTENT, response.statusCode());
                    return true;
                })
                .verifyComplete();

        verify(customerHandler).deleteCustomerById(customerId);
    }
}
