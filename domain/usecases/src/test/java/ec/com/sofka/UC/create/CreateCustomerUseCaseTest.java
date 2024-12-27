package ec.com.sofka.UC.create;

import ec.com.sofka.Account;
import ec.com.sofka.Customer;
import ec.com.sofka.gateway.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Collections;

import static org.mockito.Mockito.*;

@SpringBootTest(classes = CreateCustomerUseCaseTest.class)
public class CreateCustomerUseCaseTest {
    @InjectMocks
    private CreateCustomerUseCase createCustomerUseCase;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private Customer customer;

    @BeforeEach
    void setUp() {
        when(customer.getId()).thenReturn(1);
        when(customer.getAccounts()).thenReturn(Collections.singletonList(new Account()));
        when(customer.getAddress()).thenReturn("address");
        when(customer.getEmail()).thenReturn("email");
        when(customer.getFirstName()).thenReturn("firstName");
        when(customer.getLastName()).thenReturn("lastName");
        when(customer.getPhone()).thenReturn("phone");
    }

    @Test
    void testApply_shouldReturnCustomer() {
        when(customerRepository.createCustomer(Mockito.any(Mono.class)))
                .thenReturn(Mono.just(customer));
        Mono<Customer> result = createCustomerUseCase.apply(Mono.just(customer));
        StepVerifier.create(result)
                .expectNext(customer)
                .expectComplete()
                .verify();
        Mockito.verify(customerRepository, Mockito.times(1)).createCustomer(Mockito.any(Mono.class));
    }

    @Test
    void testApply_ShouldReturnError_WhenRepositoryFails() {
        when(customerRepository.createCustomer(Mockito.any(Mono.class)))
                .thenReturn(Mono.error(new RuntimeException("Error saving branch")));
        Mono<Customer> result = createCustomerUseCase.apply(Mono.just(customer));
        StepVerifier.create(result)
                .expectError(RuntimeException.class)
                .verify();
        verify(customerRepository, times(1)).createCustomer(Mockito.any(Mono.class));
    }
}
