package ec.com.sofka.usecase.customer;

import ec.com.sofka.Log;
import ec.com.sofka.gateway.CustomerBusMessageGateway;
import ec.com.sofka.gateway.CustomerRepositoryGateway;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

public class DeleteCustomerUseCase {

    private final CustomerRepositoryGateway customerRepositoryGateway;
    private final CustomerBusMessageGateway customerBusMessageGateway;

    public DeleteCustomerUseCase(CustomerRepositoryGateway customerRepositoryGateway, CustomerBusMessageGateway customerBusMessageGateway) {
        this.customerRepositoryGateway = customerRepositoryGateway;
        this.customerBusMessageGateway = customerBusMessageGateway;
    }

    public Mono<Void> apply(String id) {
        if (id == null) {
            return Mono.error(new IllegalArgumentException("Id must not be null"));
        }
        customerBusMessageGateway.sendMsg(new Log("Delete  customer by id: " + id, LocalDate.now()));
        return customerRepositoryGateway.deleteById(id);
    }
}
