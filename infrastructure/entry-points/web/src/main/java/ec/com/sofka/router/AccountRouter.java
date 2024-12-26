package ec.com.sofka.router;

import ec.com.sofka.ErrorResponse;
import ec.com.sofka.data.AccountRequestDTO;
import ec.com.sofka.data.AccountResponseDTO;
import ec.com.sofka.data.RequestByPOST;
import ec.com.sofka.globalexceptions.GlobalErrorHandler;
import ec.com.sofka.handler.account.AccountHandler;
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
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class AccountRouter {

    private final AccountHandler handler;
    private final ValidationService validationService;
    private final GlobalErrorHandler globalErrorHandler;

    public AccountRouter(AccountHandler handler, ValidationService validationService, GlobalErrorHandler globalErrorHandler) {
        this.handler = handler;
        this.validationService = validationService;
        this.globalErrorHandler = globalErrorHandler;
    }

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = "/accounts",
                    operation = @Operation(
                            tags = {"Accounts"},
                            operationId = "create",
                            summary = "Create a new account",
                            description = "This endpoint allows the creation of a new bank account for a user. It accepts user details in the request body and returns the created account's information.",
                            requestBody = @RequestBody(
                                    description = "Account creation details",
                                    required = true,
                                    content = @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = AccountRequestDTO.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "201",
                                            description = "Account successfully created",
                                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AccountResponseDTO.class))
                                    ),
                                    @ApiResponse(
                                            responseCode = "400",
                                            description = "Bad request, validation error or missing required fields",
                                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                                    ),
                                    @ApiResponse(
                                            responseCode = "404",
                                            description = "User not found. The provided user ID does not exist in the system.",
                                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                                    )
                            }
                    )
            ),
            @RouterOperation(
                    path = "/accounts/getByAccountNumber/{accountNumber}",
                    operation = @Operation(
                            tags = {"Accounts"},
                            operationId = "getByAccountNumber",
                            summary = "Get account by account number",
                            description = "Fetches the account details associated with the given account number. If the account does not exist, it returns a 404 Not Found error.",
                            parameters = {
                                    @Parameter(
                                            name = "accountNumber",
                                            description = "The account number to retrieve account info",
                                            required = true,
                                            in = ParameterIn.PATH
                                    )
                            },
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Successfully retrieved the account details",
                                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AccountResponseDTO.class))
                                    ),
                                    @ApiResponse(
                                            responseCode = "404",
                                            description = "Account not found. The account number does not exist in the system.",
                                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                                    )
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> accountRoutes() {
        return RouterFunctions
                .route(POST("/accounts").and(accept(MediaType.APPLICATION_JSON)), this::createAccount)
                .andRoute(GET("/accounts/getByAccountNumber/{accountNumber}"), this::getAccountByAccountNumber);
    }

    public Mono<ServerResponse> createAccount(ServerRequest request) {
        return request.bodyToMono(AccountRequestDTO.class)
                .flatMap(dto -> validationService.validate(dto, AccountRequestDTO.class))
                .flatMap(handler::createAccount)
                .flatMap(accountResponseDTO -> ServerResponse
                        .status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(accountResponseDTO))
                .onErrorResume(ex -> globalErrorHandler.handleException(request.exchange(), ex));
    }

    public Mono<ServerResponse> getAccountByAccountNumber(ServerRequest request) {
        String accountNumber = request.pathVariable("accountNumber");
        return handler.getAccountByAccountNumber(accountNumber)
                .flatMap(accountResponseDTO -> ServerResponse
                        .status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(accountResponseDTO))
                .onErrorResume(ex -> globalErrorHandler.handleException(request.exchange(), ex));
    }
}
