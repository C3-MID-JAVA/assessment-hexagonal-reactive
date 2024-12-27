package ec.com.sofka.router;

import ec.com.sofka.ErrorResponse;
import ec.com.sofka.data.AccountRequestDTO;
import ec.com.sofka.data.AccountResponseDTO;
import ec.com.sofka.data.TransactionRequestDTO;
import ec.com.sofka.data.TransactionResponseDTO;
import ec.com.sofka.globalexceptions.GlobalErrorHandler;
import ec.com.sofka.handler.AccountHandler;
import ec.com.sofka.handler.TransactionHandler;
import ec.com.sofka.mapper.AccountDTOMapper;
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
import reactor.core.publisher.Flux;
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
                    path = "/transactions/{transactionId}",
                    operation = @Operation(
                            tags = {"Transactions"},
                            operationId = "getTransactionById",
                            summary = "Get transaction by ID",
                            description = "Fetches the transaction details associated with the given transaction ID.",
                            parameters = {
                                    @Parameter(
                                            name = "transactionId",
                                            description = "The transaction ID to retrieve transaction details",
                                            required = true,
                                            in = ParameterIn.PATH
                                    )
                            },
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Successfully retrieved the transaction details",
                                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionResponseDTO.class))
                                    ),
                                    @ApiResponse(
                                            responseCode = "404",
                                            description = "Transaction not found.",
                                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                                    )
                            }
                    )
            ),
            @RouterOperation(
                    path = "/transactions",
                    operation = @Operation(
                            tags = {"Transactions"},
                            operationId = "listTransactions",
                            summary = "List all transactions",
                            description = "Fetches a list of all transactions available in the system.",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Successfully retrieved the list of transactions",
                                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionResponseDTO[].class))
                                    )
                            }
                    )
            ),
            @RouterOperation(
                    path = "/transactions/deposit",
                    operation = @Operation(
                            tags = {"Transactions"},
                            operationId = "createDeposit",
                            summary = "Create a deposit transaction",
                            description = "Creates a new deposit transaction for a user.",
                            requestBody = @RequestBody(
                                    description = "Deposit transaction details",
                                    required = true,
                                    content = @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = TransactionRequestDTO.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "201",
                                            description = "Deposit successfully created",
                                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionResponseDTO.class))
                                    ),
                                    @ApiResponse(
                                            responseCode = "400",
                                            description = "Bad request, validation error or missing required fields",
                                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                                    )
                            }
                    )
            ),
            @RouterOperation(
                    path = "/transactions/withdrawal",
                    operation = @Operation(
                            tags = {"Transactions"},
                            operationId = "createWithdrawal",
                            summary = "Create a withdrawal transaction",
                            description = "Creates a new withdrawal transaction for a user.",
                            requestBody = @RequestBody(
                                    description = "Withdrawal transaction details",
                                    required = true,
                                    content = @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = TransactionRequestDTO.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "201",
                                            description = "Withdrawal successfully created",
                                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionResponseDTO.class))
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
                .route(POST("/transactions/deposit").and(accept(MediaType.APPLICATION_JSON)), this::createDeposit)
                .andRoute(POST("/transactions/withdrawal").and(accept(MediaType.APPLICATION_JSON)), this::createWithDrawal)
                .andRoute(GET("/transactions/{transactionId}"), this::getTransactionById)
                .andRoute(GET("/transactions"), this::getAllTransactions);
    }



    public Mono<ServerResponse> createWithDrawal(ServerRequest request) {
        return request.bodyToMono(TransactionRequestDTO.class)
                .flatMap(dto -> validationService.validate(dto, TransactionRequestDTO.class))
                .flatMap(handler::createWithDrawal)
                .flatMap(transactionResponseDTO -> ServerResponse
                        .status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(transactionResponseDTO))
                .onErrorResume(ex -> globalErrorHandler.handleException(request.exchange(), ex));
    }

    public Mono<ServerResponse> createDeposit(ServerRequest request) {
        return request.bodyToMono(TransactionRequestDTO.class)
                .flatMap(dto -> validationService.validate(dto, TransactionRequestDTO.class))
                .flatMap(handler::createDeposit)
                .flatMap(transactionResponseDTO -> ServerResponse
                        .status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(transactionResponseDTO))
                .onErrorResume(ex -> globalErrorHandler.handleException(request.exchange(), ex));
    }

    public Mono<ServerResponse> getTransactionById(ServerRequest request) {
        String transactionId = request.pathVariable("transactionId");
        return handler.getTransactionById(transactionId)
                .flatMap(transactionResponseDTO -> ServerResponse
                        .status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(transactionResponseDTO))
                .onErrorResume(ex -> globalErrorHandler.handleException(request.exchange(), ex));
    }

    public Mono<ServerResponse> getAllTransactions(ServerRequest request) {
        return handler.getTransactions()
                .collectList()
                .flatMap(transactions -> ServerResponse
                        .status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(transactions))
                .onErrorResume(ex -> globalErrorHandler.handleException(request.exchange(), ex));
    }


}
