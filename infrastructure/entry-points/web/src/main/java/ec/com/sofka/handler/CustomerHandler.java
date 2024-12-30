package ec.com.sofka.handler;

import ec.com.sofka.Customer;
import ec.com.sofka.data.CustomerInDTO;
import ec.com.sofka.data.CustomerOutDTO;
import ec.com.sofka.exception.ResourceNotFoundException;
import ec.com.sofka.mapper.CustomerMapper;
import ec.com.sofka.usecase.customer.DeleteCustomerUseCase;
import ec.com.sofka.usecase.customer.FindCustomerUseCase;
import ec.com.sofka.usecase.customer.SaveCustomerUseCase;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Validated
public class CustomerHandler {

    private final SaveCustomerUseCase saveCustomerUseCase;
    private final DeleteCustomerUseCase deleteCustomerUseCase;
    private final FindCustomerUseCase findCustomerUseCase;

    public CustomerHandler(SaveCustomerUseCase saveCustomerUseCase, DeleteCustomerUseCase deleteCustomerUseCase, FindCustomerUseCase findCustomerUseCase) {
        this.saveCustomerUseCase = saveCustomerUseCase;
        this.deleteCustomerUseCase = deleteCustomerUseCase;
        this.findCustomerUseCase = findCustomerUseCase;
    }

    public Mono<CustomerOutDTO> saveCustomer(@Valid CustomerInDTO customerInDTO) {
        if (customerInDTO == null) {
            return Mono.error(new IllegalArgumentException("CustomerInDTO cannot be null"));
        }
        Customer customer = CustomerMapper.toEntity(customerInDTO);
        return saveCustomerUseCase.save(customer)
                .map(CustomerMapper::toDTO);
    }

    public Mono<CustomerOutDTO> findCustomerById(String id) {
        if (id == null || id.isEmpty()) {
            return Mono.error(new IllegalArgumentException("Customer ID cannot be null or empty"));
        }
        return findCustomerUseCase.findById(id)
                .map(CustomerMapper::toDTO)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Customer not found with id: " + id)));
    }

    public Flux<CustomerOutDTO> findAllCustomers() {
        return findCustomerUseCase.findAll()
                .map(CustomerMapper::toDTO)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("No customers found"))); // Opcional
    }

    public Mono<Void> deleteCustomerById(String id) {
        if (id == null || id.isEmpty()) {
            return Mono.error(new IllegalArgumentException("Customer ID cannot be null or empty"));
        }
        return findCustomerUseCase.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Customer not found with id: " + id)))
                .then(deleteCustomerUseCase.apply(id));
    }
}
