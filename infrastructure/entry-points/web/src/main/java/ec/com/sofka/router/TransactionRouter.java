package ec.com.sofka.router;

import ec.com.sofka.dto.request.TransactionRequestDTO;
import ec.com.sofka.dto.response.TransactionResponseDTO;
import ec.com.sofka.handler.transaction.CreateTransactionHandler;
import ec.com.sofka.handler.transaction.GetAllTransactionsHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;

@Configuration
public class TransactionRouter {

    private final CreateTransactionHandler createTransactionHandler;
    private final GetAllTransactionsHandler getAllTransactionsHandler;

    public TransactionRouter(CreateTransactionHandler createTransactionHandler, GetAllTransactionsHandler getAllTransactionsHandler) {
        this.createTransactionHandler = createTransactionHandler;
        this.getAllTransactionsHandler = getAllTransactionsHandler;
    }

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = "/api/transactions",
                    produces = {MediaType.APPLICATION_JSON_VALUE},
                    method = RequestMethod.POST,
                    beanClass = CreateTransactionHandler.class,
                    beanMethod = "handle",
                    operation = @Operation(
                            tags = {"Transactions"},
                            summary = "Create a new transaction",
                            description = "Processes a new transaction.",
                            requestBody = @RequestBody(
                                    description = "Details of the transaction",
                                    required = true,
                                    content = @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = TransactionRequestDTO.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "201",
                                            description = "Transaction created successfully",
                                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionResponseDTO.class))
                                    ),
                                    @ApiResponse(
                                            responseCode = "400",
                                            description = "Invalid transaction data",
                                            content = @Content(mediaType = "application/json")
                                    )
                            }
                    )
            ),
            @RouterOperation(
                    path = "/api/transactions",
                    produces = {MediaType.APPLICATION_JSON_VALUE},
                    method = RequestMethod.GET,
                    beanClass = GetAllTransactionsHandler.class,
                    beanMethod = "handle",
                    operation = @Operation(
                            tags = {"Transactions"},
                            summary = "Get all transactions",
                            description = "Retrieves all transactions from the system.",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "List of transactions retrieved successfully",
                                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionResponseDTO.class))
                                    ),
                                    @ApiResponse(
                                            responseCode = "204",
                                            description = "No transactions found"
                                    )
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> transactionRoutes() {
        return RouterFunctions
                .route(POST("/api/transactions").and(accept(MediaType.APPLICATION_JSON)), this::createTransaction)
                .andRoute(GET("/api/transactions").and(accept(MediaType.APPLICATION_JSON)), getAllTransactionsHandler::handle);
    }

    private Mono<ServerResponse> createTransaction(ServerRequest request) {
        return request.bodyToMono(TransactionRequestDTO.class)
                .flatMap(createTransactionHandler::handle)
                .flatMap(transactionResponse -> ServerResponse.status(201).bodyValue(transactionResponse))
                .onErrorResume(error -> ServerResponse.badRequest().bodyValue("Error: " + error.getMessage()));
    }
}
