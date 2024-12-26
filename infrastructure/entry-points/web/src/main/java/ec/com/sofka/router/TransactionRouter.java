package ec.com.sofka.router;

import ec.com.sofka.ErrorResponse;
import ec.com.sofka.data.AccountRequestDTO;
import ec.com.sofka.data.AccountResponseDTO;
import ec.com.sofka.data.TransactionRequestDTO;
import ec.com.sofka.globalexceptions.GlobalErrorHandler;
import ec.com.sofka.handler.AccountHandler;
import ec.com.sofka.handler.TransactionHandler;
import ec.com.sofka.service.ValidationService;
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
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Configuration
public class TransactionRouter {

    private final TransactionHandler handler;
    private final ValidationService validationService;
    private final GlobalErrorHandler globalErrorHandler;

    public TransactionRouter(TransactionHandler handler, ValidationService validationService, GlobalErrorHandler globalErrorHandler) {
        this.handler = handler;
        this.validationService = validationService;
        this.globalErrorHandler = globalErrorHandler;
    }


    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = "/transactions",
                    operation = @Operation(
                            tags = {"Transactions"},
                            operationId = "create",
                            summary = "Create a new transaction",
                            description = "This endpoint allows the creation of a new bank transactions for a user. It accepts user details in the request body and returns the created account's information.",
                            requestBody = @RequestBody(
                                    description = "Transaction creation details",
                                    required = true,
                                    content = @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = TransactionRequestDTO.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "201",
                                            description = "Transaction successfully created",
                                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AccountResponseDTO.class))
                                    ),
                                    @ApiResponse(
                                            responseCode = "400",
                                            description = "Bad request, validation error or missing required fields",
                                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                                    )
                            }
                    )
            )

    })
    public RouterFunction<ServerResponse> transactionRoutes() {
        return RouterFunctions
                .route(POST("/transactions").and(accept(MediaType.APPLICATION_JSON)), this::createTransaction)
                //.andRoute(GET("/accounts/getByAccountNumber/{accountNumber}"), this::getAccountByAccountNumber)
                //.andRoute(GET("/accounts/getAll"), this::listAccounts)
                //.andRoute(GET("/accounts/{accountId}"), this::getAccountById)
                //.andRoute(GET("/accounts/{accountId}/balance"), this::getAccountBalance)
        ;
    }

    public Mono<ServerResponse> createTransaction(ServerRequest request) {
        return request.bodyToMono(TransactionRequestDTO.class)
                .flatMap(dto -> validationService.validate(dto, TransactionRequestDTO.class))
                .flatMap(handler::createDeposit)
                .flatMap(transactionResponseDTO -> ServerResponse
                        .status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(transactionResponseDTO))
                .onErrorResume(ex -> globalErrorHandler.handleException(request.exchange(), ex));
    }

}
