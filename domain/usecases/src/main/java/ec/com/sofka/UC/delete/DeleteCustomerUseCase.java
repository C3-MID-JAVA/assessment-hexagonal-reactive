package ec.com.sofka.UC.delete;

import ec.com.sofka.gateway.CustomerRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class DeleteCustomerUseCase {
    private final CustomerRepository repository;

    public DeleteCustomerUseCase(CustomerRepository repository) {
        this.repository = repository;
    }

    public Mono<Void> apply(int id) {
        return null;
    }
}
