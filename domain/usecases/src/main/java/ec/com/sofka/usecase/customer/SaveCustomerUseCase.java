package ec.com.sofka.usecase.customer;

import ec.com.sofka.Customer;
import ec.com.sofka.Log;
import ec.com.sofka.gateway.CustomerBusMessageGateway;
import ec.com.sofka.gateway.CustomerRepositoryGateway;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

public class SaveCustomerUseCase {

    private final CustomerRepositoryGateway customerRepositoryGateway;
    private final CustomerBusMessageGateway customerBusMessageGateway;

    public SaveCustomerUseCase(CustomerRepositoryGateway customerRepositoryGateway, CustomerBusMessageGateway customerBusMessageGateway) {
        this.customerRepositoryGateway = customerRepositoryGateway;
        this.customerBusMessageGateway = customerBusMessageGateway;
    }

    public Mono<Customer> save(Customer entidad){
        customerBusMessageGateway.sendMsg(new Log("Saving customer: "+entidad.toString(), LocalDate.now()));
        return customerRepositoryGateway.save(entidad);
    }
}
