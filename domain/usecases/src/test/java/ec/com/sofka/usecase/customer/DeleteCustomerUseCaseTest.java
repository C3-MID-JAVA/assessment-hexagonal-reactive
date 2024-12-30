package ec.com.sofka.usecase.customer;

import ec.com.sofka.gateway.CustomerBusMessageGateway;
import ec.com.sofka.gateway.CustomerRepositoryGateway;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;


public class DeleteCustomerUseCaseTest {

    @Test
    void shouldDeleteCustomerById() {
        String customerId = "12345";
        CustomerRepositoryGateway customerRepositoryGateway = mock(CustomerRepositoryGateway.class);
        CustomerBusMessageGateway customerBusMessageGateway = mock(CustomerBusMessageGateway.class);
        DeleteCustomerUseCase deleteCustomerUseCase = new DeleteCustomerUseCase(customerRepositoryGateway, customerBusMessageGateway);

        when(customerRepositoryGateway.deleteById(customerId)).thenReturn(Mono.empty());

        Mono<Void> result = deleteCustomerUseCase.apply(customerId);

        StepVerifier.create(result)
                .verifyComplete();

        verify(customerRepositoryGateway, times(1)).deleteById(customerId);
    }

    @Test
    void shouldNotCallDeleteIfIdIsNull() {
        CustomerRepositoryGateway customerRepositoryGateway = mock(CustomerRepositoryGateway.class);
        CustomerBusMessageGateway customerBusMessageGateway = mock(CustomerBusMessageGateway.class);
        DeleteCustomerUseCase deleteCustomerUseCase = new DeleteCustomerUseCase(customerRepositoryGateway, customerBusMessageGateway);

        Mono<Void> result = deleteCustomerUseCase.apply(null);

        StepVerifier.create(result)
                .expectError(IllegalArgumentException.class)
                .verify();

        verify(customerRepositoryGateway, never()).deleteById(anyString());
    }
}
