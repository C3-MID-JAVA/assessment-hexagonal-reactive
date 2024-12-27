package com.bank.router;

import com.bank.data.OperationRequestDTO;
import com.bank.handler.OperationHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

public class OperationRouter {
    private final OperationHandler operationHandler;

    public OperationRouter(OperationHandler operationHandler) {
        this.operationHandler = operationHandler;
    }

    @Bean
    public RouterFunction<ServerResponse> operationRoutes() {
        return RouterFunctions
                .route(GET("/operations"), this::getAllOperations)
                .andRoute(GET("operations/{id}"), this::getOperationById)
                .andRoute(POST("/operations").and(accept(MediaType.APPLICATION_JSON)), this::createOperation);
    }

    public Mono<ServerResponse> getAllOperations(ServerRequest request) {
        return operationHandler.findAllOperations()
                .collectList()
                .flatMap(ops -> ServerResponse
                        .status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(ops));
    }

    public Mono<ServerResponse> getOperationById(ServerRequest request) {
        String opId = request.pathVariable("id");
        return operationHandler.findOperationById(opId)
                .flatMap(operationResponseDTO -> ServerResponse
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(operationResponseDTO));
    }

    public Mono<ServerResponse> createOperation(ServerRequest request) {
        return request.bodyToMono(OperationRequestDTO.class)
                .flatMap(operationHandler::createOperation)
                .flatMap(operationResponseDTO -> ServerResponse
                        .status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(operationResponseDTO));
    }
}
