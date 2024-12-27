package ec.com.sofka.handler;

import ec.com.sofka.UC.create.CreateCustomerUseCase;
import ec.com.sofka.UC.delete.DeleteCustomerUseCase;
import ec.com.sofka.UC.get.customer.*;
import ec.com.sofka.UC.update.UpdateCustomerUseCase;
import ec.com.sofka.customException.AlreadyExistsException;
import ec.com.sofka.customException.NotFoundException;
import ec.com.sofka.data.request.CustomerRequestDTO;
import ec.com.sofka.data.response.CustomerResponseDTO;
import ec.com.sofka.mapper.DTORequestMapper;
import ec.com.sofka.mapper.DTOResponseMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class CustomerHandler {
    private final CreateCustomerUseCase createCustomerUseCase;
    private final GetCustomerByIdUseCase getCustomerByIdUseCase;
    private final GetCustomerByFirstNameUseCase getCustomerByFirstNameUseCase;
    private final GetCustomerByLastNameUseCase getCustomerByLastNameUseCase;
    private final GetCustomerByEmailUseCase getCustomerByEmailUseCase;
    private final GetAllCustomersUseCase getAllCustomersUseCase;
    private final UpdateCustomerUseCase updateCustomerUseCase;
    private final DeleteCustomerUseCase deleteCustomerUseCase;

    public CustomerHandler(
            CreateCustomerUseCase createCustomerUseCase,
            GetCustomerByIdUseCase getCustomerByIdUseCase,
            GetCustomerByFirstNameUseCase getCustomerByFirstNameUseCase,
            GetCustomerByLastNameUseCase getCustomerByLastNameUseCase,
            GetCustomerByEmailUseCase getCustomerByEmailUseCase,
            GetAllCustomersUseCase getAllCustomersUseCase,
            UpdateCustomerUseCase updateCustomerUseCase,
            DeleteCustomerUseCase deleteCustomerUseCase
    ) {
        this.createCustomerUseCase = createCustomerUseCase;
        this.getCustomerByIdUseCase = getCustomerByIdUseCase;
        this.getCustomerByFirstNameUseCase = getCustomerByFirstNameUseCase;
        this.getCustomerByLastNameUseCase = getCustomerByLastNameUseCase;
        this.getCustomerByEmailUseCase = getCustomerByEmailUseCase;
        this.getAllCustomersUseCase = getAllCustomersUseCase;
        this.updateCustomerUseCase = updateCustomerUseCase;
        this.deleteCustomerUseCase = deleteCustomerUseCase;
    }

    public Mono<CustomerResponseDTO> createCustomer(CustomerRequestDTO customerRequestDTO) {
        return DTOResponseMapper
                .toCustomerResponseDTO
                .apply(createCustomerUseCase
                        .apply(DTORequestMapper
                                .toCustomer
                                .apply(Mono.just(customerRequestDTO))))
                .onErrorResume(e -> {
                    if (e instanceof AlreadyExistsException) {
                        return Mono.error(new ResponseStatusException(HttpStatus.CONFLICT, "Customer already exists"));
                    }
                    return Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error"));
                });
    }

    public Mono<CustomerResponseDTO> getCustomerById(CustomerRequestDTO customerRequestDTO) {
        return DTOResponseMapper
                .toCustomerResponseDTO
                .apply(getCustomerByIdUseCase
                        .apply(customerRequestDTO.getId()))
                .onErrorResume(e -> {
                    if (e instanceof NotFoundException) {
                        return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
                    }
                    return Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error"));
                });
    }

    public Mono<CustomerResponseDTO> getCustomerByFirstName(CustomerRequestDTO customerRequestDTO) {
        return DTOResponseMapper
                .toCustomerResponseDTO
                .apply(getCustomerByFirstNameUseCase
                        .apply(customerRequestDTO.getFirstName()))
                .onErrorResume(e -> {
                    if (e instanceof NotFoundException) {
                        return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
                    }
                    return Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error"));
                });
    }

    public Mono<CustomerResponseDTO> getCustomerByLastName(CustomerRequestDTO customerRequestDTO) {
        return DTOResponseMapper
                .toCustomerResponseDTO
                .apply(getCustomerByLastNameUseCase
                        .apply(customerRequestDTO.getLastName()))
                .onErrorResume(e -> {
                    if (e instanceof NotFoundException) {
                        return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
                    }
                    return Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error"));
                });
    }

    public Mono<CustomerResponseDTO> getCustomerByEmail(CustomerRequestDTO customerRequestDTO) {
        return DTOResponseMapper
                .toCustomerResponseDTO
                .apply(getCustomerByEmailUseCase
                        .apply(customerRequestDTO.getEmail()))
                .onErrorResume(e -> {
                    if (e instanceof NotFoundException) {
                        return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
                    }
                    return Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error"));
                });
    }

    public Flux<CustomerResponseDTO> getAllCustomers() {
        return getAllCustomersUseCase.apply()
                .flatMap(customer -> DTOResponseMapper.toCustomerResponseDTO.apply(Mono.just(customer)))
                .onErrorResume(e -> {
                    return Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching all customers"));
                });
    }

    public Mono<CustomerResponseDTO> updateCustomer(CustomerRequestDTO customerRequestDTO) {
        return DTOResponseMapper
                .toCustomerResponseDTO
                .apply(updateCustomerUseCase
                        .apply(DTORequestMapper
                                .toCustomer
                                .apply(Mono.just(customerRequestDTO))))
                .onErrorResume(e -> {
                    if (e instanceof NotFoundException) {
                        return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
                    }
                    return Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error"));
                });
    }

    public Mono<Void> deleteCustomer(CustomerRequestDTO customerRequestDTO) {
        return deleteCustomerUseCase.apply(customerRequestDTO.getId())
                .onErrorResume(e -> {
                    if (e instanceof NotFoundException) {
                        return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
                    }
                    return Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error"));
                });
    }

}
