package ec.com.sofka.rest;

import ec.com.sofka.data.CustomerInDTO;
import ec.com.sofka.data.CustomerOutDTO;
import ec.com.sofka.handler.CustomerHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Configuration
public class CustomerRouter {

    private final CustomerHandler customerHandler;

    public CustomerRouter(CustomerHandler customerHandler) {
        this.customerHandler = customerHandler;
    }



    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = "/v1/api/customers",
                    method = RequestMethod.POST,
                    operation = @Operation(
                            operationId = "saveCustomer",
                            summary = "Create a new customer",
                            requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = CustomerInDTO.class))),
                            responses = {
                                    @ApiResponse(responseCode = "201", description = "Customer created",
                                            content = @Content(schema = @Schema(implementation = CustomerOutDTO.class)))
                            }
                    )
            ),
            @RouterOperation(
                    path = "/v1/api/customers",
                    method = RequestMethod.PUT,
                    operation = @Operation(
                            operationId = "updateCustomer",
                            summary = "Update existing customer",
                            requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = CustomerInDTO.class)))
                    )
            ),
            @RouterOperation(
                    path = "/v1/api/customers/{id}",
                    method = RequestMethod.GET,
                    operation = @Operation(
                            operationId = "getCustomerById",
                            summary = "Get customer by ID",
                            parameters = @Parameter(name = "id", in = ParameterIn.PATH)
                    )
            ),
            @RouterOperation(
                    path = "/v1/api/customers",
                    method = RequestMethod.GET,
                    operation = @Operation(
                            operationId = "getAllCustomers",
                            summary = "Get all customers"
                    )
            ),
            @RouterOperation(
                    path = "/v1/api/customers/{id}",
                    method = RequestMethod.DELETE,
                    operation = @Operation(
                            operationId = "deleteCustomer",
                            summary = "Delete customer by ID",
                            parameters = @Parameter(name = "id", in = ParameterIn.PATH)
                    )
            )
    })
    public RouterFunction<ServerResponse> route() {
        return RouterFunctions.route()
                .POST("/v1/api/customers", this::saveCustomer)
                .PUT("/v1/api/customers", this::saveCustomer)
                .GET("/v1/api/customers/{id}", this::getCustomerById)
                .GET("/v1/api/customers", this::getAllCustomers)
                .DELETE("/v1/api/customers/{id}", this::deleteCustomer)
                .build();
    }

    public Mono<ServerResponse> saveCustomer(ServerRequest request) {
        return request.bodyToMono(CustomerInDTO.class)
                .flatMap(customerHandler::saveCustomer)
                .flatMap(customerOutDTO -> ServerResponse.status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(customerOutDTO));
    }

    public Mono<ServerResponse> getCustomerById(ServerRequest request) {
        String id = request.pathVariable("id");
        return customerHandler.findCustomerById(id)
                .flatMap(customerOutDTO -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(customerOutDTO))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getAllCustomers(ServerRequest request) {
        return customerHandler.findAllCustomers()
                .collectList()
                .flatMap(customers -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(customers));
    }

    public Mono<ServerResponse> deleteCustomer(ServerRequest request) {
        String id = request.pathVariable("id");
        return customerHandler.deleteCustomerById(id)
                .then(ServerResponse.noContent().build());
    }
}
