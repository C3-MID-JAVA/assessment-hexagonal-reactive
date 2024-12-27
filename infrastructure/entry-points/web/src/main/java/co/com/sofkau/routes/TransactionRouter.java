package co.com.sofkau.routes;


import co.com.sofkau.ErrorDetails;
import co.com.sofkau.data.CardDTO;
import co.com.sofkau.data.TransactionDTO;
import co.com.sofkau.exceptions.BodyRequestValidator;
import co.com.sofkau.exceptions.GlobalExceptionsHandler;
import co.com.sofkau.handlers.TransactionHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.List;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class TransactionRouter {

    private final TransactionHandler transactionHandler;

    private final BodyRequestValidator bodyRequestValidator;
    private final GlobalExceptionsHandler globalExceptionsHandler;


    public TransactionRouter(TransactionHandler transactionHandler, BodyRequestValidator bodyRequestValidator, GlobalExceptionsHandler globalExceptionsHandler) {
        this.transactionHandler = transactionHandler;
        this.bodyRequestValidator = bodyRequestValidator;
        this.globalExceptionsHandler = globalExceptionsHandler;
    }

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = "/api/v1/transaction/make",
                    method = RequestMethod.POST,
                    operation = @Operation(
                            tags = {"Transactions"},
                            operationId = "transaction",
                            summary = "Create a new transaction",
                            description = "Creates a new transaction with the provided details.",
                            requestBody = @RequestBody(
                                    description = "Transaction creation details",
                                    required = true,
                                    content = @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = TransactionDTO.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "201",
                                            description = "Transaction successfully created",
                                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionDTO.class))
                                    ),
                                    @ApiResponse(
                                            responseCode = "400",
                                            description = "Bad request, validation error or missing required fields",
                                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))
                                    ),
                                    @ApiResponse(
                                            responseCode = "500",
                                            description = "Card or Account could NOT exist",
                                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))
                                    )
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> transactionsRoutes() {
        return RouterFunctions
                .route(RequestPredicates.POST("/api/v1/transaction/make").and(accept(MediaType.APPLICATION_JSON)), this::crateTransaction);
    }

    public Mono<ServerResponse> crateTransaction(ServerRequest request) {

        return request.bodyToMono(TransactionDTO.class)
                .doOnNext(bodyRequestValidator::validate)
                .flatMap(transactionHandler::createTransaction)
                .flatMap(card -> ServerResponse.status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON).bodyValue(card) )
                .onErrorResume(globalExceptionsHandler::handleException);
    }
}
