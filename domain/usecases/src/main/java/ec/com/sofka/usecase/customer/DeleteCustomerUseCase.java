package ec.com.sofka.usecase.customer;

import ec.com.sofka.gateway.CustomerRepositoryGateway;
import reactor.core.publisher.Mono;

public class DeleteCustomerUseCase {

    private final CustomerRepositoryGateway customerRepositoryGateway;

    public DeleteCustomerUseCase(CustomerRepositoryGateway customerRepositoryGateway) {
        this.customerRepositoryGateway = customerRepositoryGateway;
    }

    public Mono<Void> apply(String id){
        return customerRepositoryGateway.deleteById(id);
    }
}
