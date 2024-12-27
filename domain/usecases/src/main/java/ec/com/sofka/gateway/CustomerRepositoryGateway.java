package ec.com.sofka.gateway;

import ec.com.sofka.Customer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerRepositoryGateway {

    Mono<Customer> save(Customer customer);
    Mono<Customer> findById(String id);
    Flux<Customer> findAll();
    Mono<Void> deleteById(String id);

}
