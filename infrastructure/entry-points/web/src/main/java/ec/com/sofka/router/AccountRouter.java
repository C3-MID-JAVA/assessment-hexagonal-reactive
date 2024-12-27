package ec.com.sofka.router;

import ec.com.sofka.data.request.AccountRequestDTO;
import ec.com.sofka.data.request.CustomerRequestDTO;
import ec.com.sofka.data.response.AccountResponseDTO;
import ec.com.sofka.handler.AccountHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Tag(name = "Account Management", description = "Endpoints for managing accounts")
@Configuration
public class AccountRouter {

    private final AccountHandler accountHandler;

    public AccountRouter(AccountHandler accountHandler) {
        this.accountHandler = accountHandler;
    }

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = "/accounts/create",
                    method = RequestMethod.POST,
                    operation = @Operation(
                            tags = {"accounts"},
                            operationId = "register",
                            summary = "Create a new account",
                            description = "Create a new account with the provided details",
                            requestBody = @RequestBody(
                                    description = "Account registration details",
                                    required = true,
                                    content = @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = AccountRequestDTO.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "201",
                                            description = "Account created successfully",
                                            content = @Content(
                                                    mediaType = "application/json",
                                                    schema = @Schema(implementation = AccountResponseDTO.class))
                                    ),
                                    @ApiResponse(
                                            responseCode = "400",
                                            description = "Invalid input data")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/accounts/id",
                    method = RequestMethod.POST,
                    operation = @Operation(
                            tags = {"accounts"},
                            operationId = "getAccountById",
                            summary = "Retrieve an account by ID",
                            description = "Get the details of an account using its ID",
                            requestBody = @RequestBody(
                                    description = "Account registration details",
                                    required = true,
                                    content = @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = AccountRequestDTO.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Account retrieved successfully",
                                            content = @Content(
                                                    mediaType = "application/json",
                                                    schema = @Schema(implementation = AccountResponseDTO.class))
                                    ),
                                    @ApiResponse(
                                            responseCode = "404",
                                            description = "Account not found")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/accounts/customer",
                    method = RequestMethod.POST,
                    operation = @Operation(
                            tags = {"accounts"},
                            operationId = "getAccountsByCustomerId",
                            summary = "Retrieve accounts by Customer ID",
                            description = "Get the details of accounts using the customer's ID",
                            requestBody = @RequestBody(
                                    description = "Customer details",
                                    required = true,
                                    content = @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = CustomerRequestDTO.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Accounts retrieved successfully",
                                            content = @Content(
                                                    mediaType = "application/json",
                                                    schema = @Schema(implementation = AccountResponseDTO.class))
                                    ),
                                    @ApiResponse(
                                            responseCode = "404",
                                            description = "No accounts found for the customer")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/accounts",
                    method = RequestMethod.GET,
                    operation = @Operation(
                            tags = {"accounts"},
                            operationId = "getAllAccounts",
                            summary = "Retrieve all accounts",
                            description = "Get the list of all accounts",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "List of accounts retrieved successfully",
                                            content = @Content(
                                                    mediaType = "application/json",
                                                    schema = @Schema(implementation = AccountResponseDTO[].class))
                                    ),
                                    @ApiResponse(
                                            responseCode = "404",
                                            description = "No accounts found")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/accounts",
                    method = RequestMethod.PUT,
                    operation = @Operation(
                            tags = {"accounts"},
                            operationId = "updateAccount",
                            summary = "Update an account",
                            description = "Update an existing account with new details",
                            requestBody = @RequestBody(
                                    description = "Updated account details",
                                    required = true,
                                    content = @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = AccountRequestDTO.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Account updated successfully",
                                            content = @Content(
                                                    mediaType = "application/json",
                                                    schema = @Schema(implementation = AccountResponseDTO.class))
                                    ),
                                    @ApiResponse(
                                            responseCode = "400",
                                            description = "Invalid input data"),
                                    @ApiResponse(
                                            responseCode = "404",
                                            description = "Account not found")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/accounts/delete",
                    method = RequestMethod.POST,
                    operation = @Operation(
                            tags = {"accounts"},
                            operationId = "deleteAccount",
                            summary = "Delete an account",
                            description = "Delete an account by its ID",
                            requestBody = @RequestBody(
                                    description = "Deleted account details",
                                    required = true,
                                    content = @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = AccountRequestDTO.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "204",
                                            description = "Account deleted successfully"),
                                    @ApiResponse(
                                            responseCode = "404",
                                            description = "Account not found")
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> accountRoutes() {
        return route(POST("/accounts/create"), this::createAccount)
                .andRoute(POST("/accounts/id"), this::getAccountById)
                .andRoute(POST("/accounts/customer"), this::getAccountsByCustomerId)
                .andRoute(GET("/accounts"), this::getAllAccounts)
                .andRoute(PUT("/accounts"), this::updateAccount)
                .andRoute(POST("/accounts/delete"), this::deleteAccount);
    }

    private Mono<ServerResponse> createAccount(ServerRequest request) {
        return request.bodyToMono(AccountRequestDTO.class)
                .flatMap(accountHandler::createAccount)
                .flatMap(accountResponseDTO -> ServerResponse.status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON).bodyValue(accountResponseDTO));
    }

    private Mono<ServerResponse> getAccountById(ServerRequest request) {
        return request.bodyToMono(AccountRequestDTO.class)
                .flatMap(accountHandler::getAccountById)
                .flatMap(accountResponseDTO -> ServerResponse.status(HttpStatus.FOUND)
                        .contentType(MediaType.APPLICATION_JSON).bodyValue(accountResponseDTO));
    }

    private Mono<ServerResponse> getAccountsByCustomerId(ServerRequest request) {
        return request.bodyToMono(CustomerRequestDTO.class)
                .flatMapMany(accountHandler::getAccountsByCustomerId)
                .collectList()
                .flatMap(accountResponseDTOs -> ServerResponse.status(accountResponseDTOs.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.FOUND)
                        .contentType(MediaType.APPLICATION_JSON).bodyValue(accountResponseDTOs));
    }

    private Mono<ServerResponse> getAllAccounts(ServerRequest request) {
        return accountHandler.getAllAccounts()
                .collectList()
                .flatMap(accountResponseDTOs -> ServerResponse.status(accountResponseDTOs.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON).bodyValue(accountResponseDTOs));
    }

    private Mono<ServerResponse> updateAccount(ServerRequest request) {
        return request.bodyToMono(AccountRequestDTO.class)
                .flatMap(accountHandler::updateAccount)
                .flatMap(accountResponseDTO -> ServerResponse.status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON).bodyValue(accountResponseDTO))
                .switchIfEmpty(ServerResponse.status(HttpStatus.NOT_FOUND).build());
    }

    private Mono<ServerResponse> deleteAccount(ServerRequest request) {
        return request.bodyToMono(AccountRequestDTO.class)
                .flatMap(accountHandler::deleteAccount)
                .then(ServerResponse.noContent().build())
                .onErrorResume(e -> ServerResponse.status(HttpStatus.NOT_FOUND).build());
    }
}