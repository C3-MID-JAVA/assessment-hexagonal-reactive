package ec.com.sofka.adapter;

import ec.com.sofka.Customer;
import ec.com.sofka.data.CustomerDocument;
import ec.com.sofka.gateway.CustomerRepository;
import ec.com.sofka.mapper.DocumentToModelMapper;
import ec.com.sofka.mapper.ModelToDocumentMapper;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class CustomerAdapter implements CustomerRepository {

    private final ReactiveMongoTemplate reactiveMongoTemplate;

    public CustomerAdapter(ReactiveMongoTemplate reactiveMongoTemplate) {
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }

    @Override
    public Mono<Customer> createCustomer(Mono<Customer> customer) {
        return customer.flatMap(customerModel -> ModelToDocumentMapper.toCustomer.apply(Mono.just(customerModel)))
                .flatMap(reactiveMongoTemplate::save)
                .flatMap(customerDocument -> DocumentToModelMapper.toCustomer.apply(Mono.just(customerDocument)));
    }

    @Override
    public Mono<Customer> findCustomerById(Mono<Integer> id) {
        return id.flatMap(customerId -> reactiveMongoTemplate.findById(customerId, CustomerDocument.class))
                .flatMap(customerDocument -> DocumentToModelMapper.toCustomer.apply(Mono.just(customerDocument)));
    }

    @Override
    public Mono<Customer> findCustomerByFirstName(Mono<String> first_name) {
        return first_name.flatMap(customerFirstName -> reactiveMongoTemplate
                        .findOne(Query.query(Criteria.where("firstName").is(customerFirstName)), CustomerDocument.class))
                .flatMap(customerDocument -> DocumentToModelMapper.toCustomer.apply(Mono.just(customerDocument)));
    }

    @Override
    public Mono<Customer> findCustomerByLastName(Mono<String> last_name) {
        return last_name.flatMap(customerLastName -> reactiveMongoTemplate
                        .findOne(Query.query(Criteria.where("firstName").is(customerLastName)), CustomerDocument.class))
                .flatMap(customerDocument -> DocumentToModelMapper.toCustomer.apply(Mono.just(customerDocument)));
    }

    @Override
    public Mono<Customer> findCustomerByEmail(Mono<String> email) {
        return email.flatMap(customerEmail -> reactiveMongoTemplate
                        .findOne(Query.query(Criteria.where("email").is(customerEmail)), CustomerDocument.class))
                .flatMap(customerDocument -> DocumentToModelMapper.toCustomer.apply(Mono.just(customerDocument)));
    }

    @Override
    public Flux<Customer> findAll() {
        return reactiveMongoTemplate.findAll(CustomerDocument.class)
                .flatMap(customerDocument -> DocumentToModelMapper.toCustomer.apply(Mono.just(customerDocument)));
    }

    @Override
    public Mono<Void> deleteById(Mono<Integer> id) {
        return id.flatMap(customerId -> reactiveMongoTemplate
                        .remove(Query.query(Criteria.where("_id").is(customerId)), CustomerDocument.class))
                .then();
    }
}
