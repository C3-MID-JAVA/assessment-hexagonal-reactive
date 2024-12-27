package ec.com.sofka.routes;

import ec.com.sofka.RegisterTransactionUseCase;
import ec.com.sofka.data.TransactionRequestDTO;
import ec.com.sofka.data.TransactionResponseDTO;
import ec.com.sofka.handlers.TransactionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.*;
import org.springframework.http.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class TransactionRouter {

    private final TransactionHandler transactionHandler;

    public TransactionRouter(TransactionHandler transactionHandler) {
        this.transactionHandler = transactionHandler;
    }

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = "/transactions",
                    operation = @Operation(
                            tags = {"Transactions"},
                            operationId = "create",
                            summary = "Create a new transaction",
                            description = "Creates a new transaction for the account with the provided id.",
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
                                            responseCode = "200",
                                            description = "Transaction registered successfully",
                                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionResponseDTO.class))
                                    ),
                                    @ApiResponse(
                                            responseCode = "404",
                                            description = "Account with provided account number not found",
                                            content = @Content(mediaType = "application/json")
                                    ),
                                    @ApiResponse(
                                            responseCode = "404",
                                            description = "Insufficient balance for this transaction",
                                            content = @Content(mediaType = "application/json")
                                    )
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> transactionRoutes() {
        return RouterFunctions
                .route(RequestPredicates.POST("/transactions")
                        .and(accept(MediaType.APPLICATION_JSON)), this::registerTransaction)
                .andRoute(RequestPredicates.GET("/transactions/{accountNumber}"), this::getTransactionsByAccount);
    }

    public Mono<ServerResponse> registerTransaction(ServerRequest request){
        return request.bodyToMono(TransactionRequestDTO.class)
                .flatMap(transactionHandler::registerTransaction)
                .flatMap(transactionResponseDTO -> ServerResponse
                        .status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(transactionResponseDTO));
    }

    public Mono<ServerResponse> getTransactionsByAccount(ServerRequest request){
        return null;
    }
}