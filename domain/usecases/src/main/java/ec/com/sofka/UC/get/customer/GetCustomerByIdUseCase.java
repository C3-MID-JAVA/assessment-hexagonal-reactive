package ec.com.sofka.UC.get.customer;

import ec.com.sofka.Customer;
import ec.com.sofka.gateway.CustomerRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class GetCustomerByIdUseCase {
    private final CustomerRepository repository;

    public GetCustomerByIdUseCase(CustomerRepository repository) {
        this.repository = repository;
    }

    public Mono<Customer> apply(int id) {
        return null;
    }
}
