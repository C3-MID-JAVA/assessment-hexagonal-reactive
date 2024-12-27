package ec.com.sofka.usecase.customer;

import ec.com.sofka.Customer;
import ec.com.sofka.gateway.CustomerRepositoryGateway;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;

import static org.mockito.Mockito.*;

public class SaveCustomerUseCaseTest {

    @Test
    void shouldSaveCustomer() {
        Customer customer = new Customer(
                "12345",
                "ID123",
                "John",
                "Doe",
                "john.doe@example.com",
                "123-456-7890",
                "123 Main St",
                LocalDate.of(1990, 1, 1)
        );
        CustomerRepositoryGateway customerRepositoryGateway = mock(CustomerRepositoryGateway.class);
        SaveCustomerUseCase saveCustomerUseCase = new SaveCustomerUseCase(customerRepositoryGateway);

        when(customerRepositoryGateway.save(customer)).thenReturn(Mono.just(customer));

        Mono<Customer> result = saveCustomerUseCase.save(customer);

        StepVerifier.create(result)
                .expectNext(customer)
                .verifyComplete();

        verify(customerRepositoryGateway, times(1)).save(customer);
    }

    @Test
    void shouldReturnErrorWhenSaveFails() {
        Customer customer = new Customer(
                "12345",
                "ID123",
                "John",
                "Doe",
                "john.doe@example.com",
                "123-456-7890",
                "123 Main St",
                LocalDate.of(1990, 1, 1)
        );
        CustomerRepositoryGateway customerRepositoryGateway = mock(CustomerRepositoryGateway.class);
        SaveCustomerUseCase saveCustomerUseCase = new SaveCustomerUseCase(customerRepositoryGateway);

        when(customerRepositoryGateway.save(customer)).thenReturn(Mono.error(new RuntimeException("Save failed")));

        Mono<Customer> result = saveCustomerUseCase.save(customer);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Save failed"))
                .verify();

        verify(customerRepositoryGateway, times(1)).save(customer);
    }

}
