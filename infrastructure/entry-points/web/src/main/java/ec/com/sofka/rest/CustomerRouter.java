package ec.com.sofka.rest;

import ec.com.sofka.data.CustomerInDTO;
import ec.com.sofka.handler.CustomerHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

//    @Bean
//    public RouterFunction<ServerResponse> accountsRouters() {
//        return RouterFunctions
//                .route(POST("/api/customers").and(accept(APPLICATION_JSON)), this::saveCustomer)
//                .andRoute(GET("/api/customers"), this::getAllCustomers)
//                .andRoute(GET("/api/customers/{id}"), this::getCustomerById);
//    }

    @Bean
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
