package ec.com.sofka;

import ec.com.sofka.data.dto.transactionDTO.TransactionRequestDTO;
import ec.com.sofka.data.dto.transactionDTO.TransactionResponseDTO;
import ec.com.sofka.handlers.transaction.GetTransactionByIdHandler;
import ec.com.sofka.handlers.transaction.SaveTransactionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RestTransaction {
    private final SaveTransactionHandler saveTransactionHandler;
    private final GetTransactionByIdHandler getTransactionByIdHandler;
    private final Logger logger = LoggerFactory.getLogger(RestTransaction.class);

    public RestTransaction(SaveTransactionHandler saveTransactionHandler, GetTransactionByIdHandler getTransactionByIdHandler) {
        this.saveTransactionHandler = saveTransactionHandler;
        this.getTransactionByIdHandler = getTransactionByIdHandler;
    }

    @Bean
    public RouterFunction<ServerResponse> transactionRoutes() {
        return route(GET("/api/transacciones/{id}"), this::getTransactionById)
                .andRoute(POST("/api/transacciones"), this::saveTransaction)
                .andRoute(GET("/api/**"), request ->
                        ServerResponse.status(HttpStatus.NOT_FOUND)
                                .bodyValue("The requested resource does not exist transaction"));
    }

    public Mono<ServerResponse> saveTransaction(ServerRequest request) {
        return request.bodyToMono(TransactionRequestDTO.class)
                .flatMap(saveTransactionHandler::handle)
                .flatMap(transaction -> ServerResponse.status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(transaction))
                .onErrorResume(e -> ServerResponse.status(HttpStatus.BAD_REQUEST)
                        .bodyValue(e.getMessage()));
    }
/*
    public Mono<ServerResponse> getTransactionById(ServerRequest request) {
        String id = request.pathVariable("id");
        logger.info("Received request for transaction ID: {}", id);

        return getTransactionByIdHandler.handle(id)
                .flatMap(responseDTO -> {
                    logger.info("Transaction found: {}", responseDTO);
                    return ServerResponse.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(responseDTO);
                })
                .switchIfEmpty(ServerResponse.status(HttpStatus.NOT_FOUND)
                        .bodyValue("La transaccion con el ID " + id + " no existe"));
    }*/
public Mono<ServerResponse> getTransactionById(ServerRequest request) {
    String id = request.pathVariable("id");
    logger.info("Received request for transaction ID: {}", id);

    return getTransactionByIdHandler.handle(id)
            .flatMap(responseDTO -> {
                logger.info("Transaction found: {}", responseDTO);
                return ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(responseDTO);
            })
            .switchIfEmpty(ServerResponse.status(HttpStatus.NOT_FOUND)
                    .bodyValue("La transaccion con el ID " + id + " no existe"));
}
}