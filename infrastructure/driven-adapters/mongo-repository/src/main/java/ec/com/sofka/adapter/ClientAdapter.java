package ec.com.sofka.adapter;

import ec.com.sofka.Customer;
import ec.com.sofka.document.ClientEntity;
import ec.com.sofka.gateway.CustomerRepositoryGateway;
import ec.com.sofka.mapper.CustomerRepoMapper;
import ec.com.sofka.repository.ClientRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class ClientAdapter implements CustomerRepositoryGateway {

    private final ClientRepository clientRepository;

    public ClientAdapter(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Mono<Customer> save(Customer customer) {
        ClientEntity clientEntity = CustomerRepoMapper.toEntity(customer);
        return clientRepository.save(clientEntity)
                .map(CustomerRepoMapper::toDomain);
    }

    @Override
    public Mono<Customer> findById(String id) {
        return clientRepository.findById(id)
                .map(CustomerRepoMapper::toDomain);
    }

    @Override
    public Flux<Customer> findAll() {
        return clientRepository.findAll()
                .map(CustomerRepoMapper::toDomain);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return clientRepository.deleteById(id);
    }
}
