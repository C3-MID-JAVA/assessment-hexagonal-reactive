package ec.com.sofka.gateway;

import ec.com.sofka.Customer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerRepository {

    Mono<Customer> createCustomer(Mono<Customer> customer);

    Mono<Customer> findCustomerById(Mono<Integer> id);

    Mono<Customer> findCustomerByFirstName(Mono<String> first_name);

    Mono<Customer> findCustomerByLastName(Mono<String> last_name);

    Mono<Customer> findCustomerByEmail(Mono<String> email);

    Flux<Customer> findAll();

    Mono<Void> deleteById(Mono<Integer> id);
}
