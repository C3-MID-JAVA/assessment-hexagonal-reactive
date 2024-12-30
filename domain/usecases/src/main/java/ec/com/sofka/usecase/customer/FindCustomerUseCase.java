package ec.com.sofka.usecase.customer;

import ec.com.sofka.Customer;
import ec.com.sofka.Log;
import ec.com.sofka.gateway.CustomerBusMessageGateway;
import ec.com.sofka.gateway.CustomerRepositoryGateway;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

public class FindCustomerUseCase {

    private final CustomerRepositoryGateway customerRepositoryGateway;
    private final CustomerBusMessageGateway customerBusMessageGateway;

    public FindCustomerUseCase(CustomerRepositoryGateway customerRepositoryGateway, CustomerBusMessageGateway customerBusMessageGateway) {
        this.customerRepositoryGateway = customerRepositoryGateway;
        this.customerBusMessageGateway = customerBusMessageGateway;
    }

    public Mono<Customer> findById(String id){
        customerBusMessageGateway.sendMsg(new Log("Searching customer by ID: "+id, LocalDate.now()));
        return customerRepositoryGateway.findById(id);
    }

    public Flux<Customer> findAll(){
        customerBusMessageGateway.sendMsg(new Log("Searching all  customer", LocalDate.now()));
        return customerRepositoryGateway.findAll();
    }
}
