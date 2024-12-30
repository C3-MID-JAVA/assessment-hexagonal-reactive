package ec.com.sofka.usecase.customer;

import ec.com.sofka.Customer;
import ec.com.sofka.gateway.CustomerRepositoryGateway;
import reactor.core.publisher.Mono;

public class SaveCustomerUseCase {

    private final CustomerRepositoryGateway customerRepositoryGateway;

    public SaveCustomerUseCase(CustomerRepositoryGateway customerRepositoryGateway) {
        this.customerRepositoryGateway = customerRepositoryGateway;
    }

    public Mono<Customer> save(Customer entidad){
        return customerRepositoryGateway.save(entidad);
    }
}
