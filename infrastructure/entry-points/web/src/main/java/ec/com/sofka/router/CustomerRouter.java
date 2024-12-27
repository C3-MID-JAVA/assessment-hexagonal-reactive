package ec.com.sofka.router;

import ec.com.sofka.data.request.CustomerRequestDTO;
import ec.com.sofka.data.response.CustomerResponseDTO;
import ec.com.sofka.handler.CustomerHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Tag(name = "Customer Management", description = "Endpoints for managing customers")
@Configuration
public class CustomerRouter {

    private final CustomerHandler customerHandler;

    public CustomerRouter(CustomerHandler customerHandler) {
        this.customerHandler = customerHandler;
    }

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = "/customers/create",
                    method = RequestMethod.POST,
                    operation = @Operation(
                            tags = {"customers"},
                            operationId = "createCustomer",
                            summary = "Create a new customer",
                            description = "Add a new customer to the system",
                            requestBody = @RequestBody(
                                    description = "Customer creation details",
                                    required = true,
                                    content = @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = CustomerRequestDTO.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "201",
                                            description = "Customer created successfully",
                                            content = @Content(
                                                    mediaType = "application/json",
                                                    schema = @Schema(implementation = CustomerResponseDTO.class)
                                            )
                                    ),
                                    @ApiResponse(responseCode = "400", description = "Invalid input data")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/customers/id",
                    method = RequestMethod.POST,
                    operation = @Operation(
                            tags = {"customers"},
                            operationId = "getCustomerById",
                            summary = "Retrieve a customer by ID",
                            description = "Get details of a customer by their unique ID",
                            requestBody = @RequestBody(
                                    description = "Customer details",
                                    required = true,
                                    content = @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = CustomerRequestDTO.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Customer retrieved successfully",
                                            content = @Content(
                                                    mediaType = "application/json",
                                                    schema = @Schema(implementation = CustomerResponseDTO.class)
                                            )
                                    ),
                                    @ApiResponse(responseCode = "404", description = "Customer not found")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/customers/firstName",
                    method = RequestMethod.POST,
                    operation = @Operation(
                            tags = {"customers"},
                            operationId = "getCustomerByFirstName",
                            summary = "Retrieve a customer by first name",
                            description = "Get details of a customer by their first name",
                            requestBody = @RequestBody(
                                    description = "Customer details",
                                    required = true,
                                    content = @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = CustomerRequestDTO.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Customer retrieved successfully",
                                            content = @Content(
                                                    mediaType = "application/json",
                                                    schema = @Schema(implementation = CustomerResponseDTO.class)
                                            )
                                    ),
                                    @ApiResponse(responseCode = "404", description = "Customer not found")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/customers/lastName",
                    method = RequestMethod.POST,
                    operation = @Operation(
                            tags = {"customers"},
                            operationId = "getCustomerByLastName",
                            summary = "Retrieve a customer by last name",
                            description = "Get details of a customer by their last name",
                            requestBody = @RequestBody(
                                    description = "Customer details",
                                    required = true,
                                    content = @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = CustomerRequestDTO.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Customer retrieved successfully",
                                            content = @Content(
                                                    mediaType = "application/json",
                                                    schema = @Schema(implementation = CustomerResponseDTO.class)
                                            )
                                    ),
                                    @ApiResponse(responseCode = "404", description = "Customer not found")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/customers/email",
                    method = RequestMethod.POST,
                    operation = @Operation(
                            tags = {"customers"},
                            operationId = "getCustomerByEmail",
                            summary = "Retrieve a customer by email",
                            description = "Get details of a customer by their email address",
                            requestBody = @RequestBody(
                                    description = "Customer details",
                                    required = true,
                                    content = @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = CustomerRequestDTO.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Customer retrieved successfully",
                                            content = @Content(
                                                    mediaType = "application/json",
                                                    schema = @Schema(implementation = CustomerResponseDTO.class)
                                            )
                                    ),
                                    @ApiResponse(responseCode = "404", description = "Customer not found")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/customers",
                    method = RequestMethod.GET,
                    operation = @Operation(
                            tags = {"customers"},
                            operationId = "getAllCustomers",
                            summary = "Retrieve all customers",
                            description = "Get a list of all customers in the system",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Customers retrieved successfully",
                                            content = @Content(
                                                    mediaType = "application/json",
                                                    schema = @Schema(implementation = CustomerResponseDTO[].class)
                                            )
                                    )
                            }
                    )
            ),
            @RouterOperation(
                    path = "/customers",
                    method = RequestMethod.PUT,
                    operation = @Operation(
                            tags = {"customers"},
                            operationId = "updateCustomer",
                            summary = "Update a customer",
                            description = "Update details of an existing customer",
                            requestBody = @RequestBody(
                                    description = "Updated customer details",
                                    required = true,
                                    content = @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = CustomerRequestDTO.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Customer updated successfully",
                                            content = @Content(
                                                    mediaType = "application/json",
                                                    schema = @Schema(implementation = CustomerResponseDTO.class)
                                            )
                                    ),
                                    @ApiResponse(responseCode = "400", description = "Invalid input data"),
                                    @ApiResponse(responseCode = "404", description = "Customer not found")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/customers/delete",
                    method = RequestMethod.POST,
                    operation = @Operation(
                            tags = {"customers"},
                            operationId = "deleteCustomer",
                            summary = "Delete a customer",
                            description = "Remove a customer from the system by their ID",
                            requestBody = @RequestBody(
                                    description = "Deleted customer details",
                                    required = true,
                                    content = @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = CustomerRequestDTO.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(responseCode = "204", description = "Customer deleted successfully"),
                                    @ApiResponse(responseCode = "404", description = "Customer not found")
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> customerRoutes() {
        return route(POST("/customers/create"), this::createCustomer)
                .andRoute(POST("/customers/id"), this::getCustomerById)
                .andRoute(POST("/customers/firstName"), this::getCustomerByFirstName)
                .andRoute(POST("/customers/lastName"), this::getCustomerByLastName)
                .andRoute(POST("/customers/email"), this::getCustomerByEmail)
                .andRoute(GET("/customers"), this::getAllCustomers)
                .andRoute(PUT("/customers"), this::updateCustomer)
                .andRoute(POST("/customers/delete"), this::deleteCustomer);
    }

    private Mono<ServerResponse> createCustomer(ServerRequest request) {
        return request.bodyToMono(CustomerRequestDTO.class)
                .flatMap(customerHandler::createCustomer)
                .flatMap(customerResponseDTO -> ServerResponse.status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON).bodyValue(customerResponseDTO));
    }

    private Mono<ServerResponse> getCustomerById(ServerRequest request) {
        return request.bodyToMono(CustomerRequestDTO.class)
                .flatMap(customerHandler::getCustomerById)
                .flatMap(customerResponseDTO -> ServerResponse.status(HttpStatus.FOUND)
                        .contentType(MediaType.APPLICATION_JSON).bodyValue(customerResponseDTO))
                .switchIfEmpty(ServerResponse.status(HttpStatus.NOT_FOUND).build());
    }

    private Mono<ServerResponse> getCustomerByFirstName(ServerRequest request) {
        return request.bodyToMono(CustomerRequestDTO.class)
                .flatMap(customerHandler::getCustomerByFirstName)
                .flatMap(customerResponseDTO -> ServerResponse.status(HttpStatus.FOUND)
                        .contentType(MediaType.APPLICATION_JSON).bodyValue(customerResponseDTO))
                .switchIfEmpty(ServerResponse.status(HttpStatus.NOT_FOUND).build());
    }

    private Mono<ServerResponse> getCustomerByLastName(ServerRequest request) {
        return request.bodyToMono(CustomerRequestDTO.class)
                .flatMap(customerHandler::getCustomerByLastName)
                .flatMap(customerResponseDTO -> ServerResponse.status(HttpStatus.FOUND)
                        .contentType(MediaType.APPLICATION_JSON).bodyValue(customerResponseDTO))
                .switchIfEmpty(ServerResponse.status(HttpStatus.NOT_FOUND).build());
    }

    private Mono<ServerResponse> getCustomerByEmail(ServerRequest request) {
        return request.bodyToMono(CustomerRequestDTO.class)
                .flatMap(customerHandler::getCustomerByEmail)
                .flatMap(customerResponseDTO -> ServerResponse.status(HttpStatus.FOUND)
                        .contentType(MediaType.APPLICATION_JSON).bodyValue(customerResponseDTO))
                .switchIfEmpty(ServerResponse.status(HttpStatus.NOT_FOUND).build());
    }

    private Mono<ServerResponse> getAllCustomers(ServerRequest request) {
        return customerHandler.getAllCustomers()
                .collectList()
                .flatMap(customerResponseDTOs -> ServerResponse.status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON).bodyValue(customerResponseDTOs));
    }

    private Mono<ServerResponse> updateCustomer(ServerRequest request) {
        return request.bodyToMono(CustomerRequestDTO.class)
                .flatMap(customerHandler::updateCustomer)
                .flatMap(customerResponseDTO -> ServerResponse.status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON).bodyValue(customerResponseDTO))
                .switchIfEmpty(ServerResponse.status(HttpStatus.NOT_FOUND).build());
    }

    private Mono<ServerResponse> deleteCustomer(ServerRequest request) {
        return request.bodyToMono(CustomerRequestDTO.class)
                .flatMap(customerHandler::deleteCustomer)
                .then(ServerResponse.noContent().build())
                .onErrorResume(e -> ServerResponse.status(HttpStatus.NOT_FOUND).build());
    }
}
