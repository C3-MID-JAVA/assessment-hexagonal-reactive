package ec.com.sofka.usecase.customer;

import ec.com.sofka.Customer;
import ec.com.sofka.gateway.CustomerRepositoryGateway;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;


public class FindCustomerUseCaseTest {

    @Test
    void shouldFindCustomerById() {
        String customerId = "12345";
        Customer expectedCustomer = new Customer(
                customerId,
                "ID123",
                "John",
                "Doe",
                "john.doe@example.com",
                "123-456-7890",
                "123 Main St",
                LocalDate.of(1990, 1, 1)
        );
        CustomerRepositoryGateway customerRepositoryGateway = mock(CustomerRepositoryGateway.class);
        FindCustomerUseCase findCustomerUseCase = new FindCustomerUseCase(customerRepositoryGateway);

        when(customerRepositoryGateway.findById(customerId)).thenReturn(Mono.just(expectedCustomer));

        Mono<Customer> result = findCustomerUseCase.findById(customerId);

        StepVerifier.create(result)
                .expectNext(expectedCustomer)
                .verifyComplete();

        verify(customerRepositoryGateway, times(1)).findById(customerId);
    }

    @Test
    void shouldReturnEmptyWhenCustomerNotFoundById() {
        String customerId = "1231213";
        CustomerRepositoryGateway customerRepositoryGateway = mock(CustomerRepositoryGateway.class);
        FindCustomerUseCase findCustomerUseCase = new FindCustomerUseCase(customerRepositoryGateway);

        when(customerRepositoryGateway.findById(customerId)).thenReturn(Mono.empty());

        Mono<Customer> result = findCustomerUseCase.findById(customerId);

        StepVerifier.create(result)
                .verifyComplete();

        verify(customerRepositoryGateway, times(1)).findById(customerId);
    }

    @Test
    void shouldFindAllCustomers() {
        List<Customer> customers = Arrays.asList(
                new Customer("123", "ID123", "John", "Doe", "john.doe@example.com", "123-456-7890", "123 Main St", LocalDate.of(1990, 1, 1)),
                new Customer("124", "ID124", "Jane", "Doe", "jane.doe@example.com", "987-654-3210", "456 Elm St", LocalDate.of(1992, 2, 2))
        );
        CustomerRepositoryGateway customerRepositoryGateway = mock(CustomerRepositoryGateway.class);
        FindCustomerUseCase findCustomerUseCase = new FindCustomerUseCase(customerRepositoryGateway);

        when(customerRepositoryGateway.findAll()).thenReturn(Flux.fromIterable(customers));

        Flux<Customer> result = findCustomerUseCase.findAll();

        StepVerifier.create(result)
                .expectNextSequence(customers)
                .verifyComplete();

        verify(customerRepositoryGateway, times(1)).findAll();
    }

    @Test
    void shouldReturnEmptyWhenNoCustomersFound() {
        CustomerRepositoryGateway customerRepositoryGateway = mock(CustomerRepositoryGateway.class);
        FindCustomerUseCase findCustomerUseCase = new FindCustomerUseCase(customerRepositoryGateway);

        when(customerRepositoryGateway.findAll()).thenReturn(Flux.empty());

        Flux<Customer> result = findCustomerUseCase.findAll();

        StepVerifier.create(result)
                .verifyComplete();

        verify(customerRepositoryGateway, times(1)).findAll();
    }

}
