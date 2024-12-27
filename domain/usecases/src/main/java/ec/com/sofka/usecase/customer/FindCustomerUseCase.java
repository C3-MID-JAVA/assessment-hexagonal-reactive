package ec.com.sofka.usecase.customer;

import ec.com.sofka.Customer;
import ec.com.sofka.gateway.CustomerRepositoryGateway;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class FindCustomerUseCase {

    private final CustomerRepositoryGateway customerRepositoryGateway;

    public FindCustomerUseCase(CustomerRepositoryGateway customerRepositoryGateway) {
        this.customerRepositoryGateway = customerRepositoryGateway;
    }

    public Mono<Customer> findById(String id){
        return customerRepositoryGateway.findById(id);
    }

    public Flux<Customer> findAll(){
        return customerRepositoryGateway.findAll();
    }
}
