package ec.com.sofka.routes;

import ec.com.sofka.GetAllAccountsUseCase;
import ec.com.sofka.RegisterTransactionUseCase;
import ec.com.sofka.data.AccountRequestDTO;
import ec.com.sofka.data.AccountResponseDTO;
import ec.com.sofka.handlers.AccountHandler;
import ec.com.sofka.handlers.TransactionHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class AccountRouter {

    private final AccountHandler accountHandler;

    public AccountRouter(AccountHandler accountHandler) {
        this.accountHandler = accountHandler;
    }


    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = "/accounts",
                    operation = @Operation(
                            tags = {"Accounts"},
                            operationId = "create",
                            summary = "Create a new account",
                            description = "Creates a new bank account in the system.",
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
                                            content = @Content(mediaType = "application/json")
                                    )
                            }
                    )
            ),
            @RouterOperation(
                    path = "/accounts",
                    operation = @Operation(
                            tags = {"Accounts"},
                            operationId = "getAll",
                            summary = "Get all accounts ",
                            description = "Retrieve all accounts with their respective details",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Accounts retrieved successfully",
                                            content = @Content(mediaType = "application/json")
                                    ),
                                    @ApiResponse(
                                            responseCode = "204",
                                            description = "No content found",
                                            content = @Content(mediaType = "application/json")
                                    )
                            }
                    )
            ),
    })
    public RouterFunction<ServerResponse> accountRoutes(){
        return RouterFunctions
                .route(RequestPredicates.POST("/accounts").and(accept(MediaType.APPLICATION_JSON)), this::createAccount)
                .andRoute(RequestPredicates.GET("/accounts"), this::getAllAccounts);
    }

    public Mono<ServerResponse> createAccount(ServerRequest request) {
        return request.bodyToMono(AccountRequestDTO.class)
                .flatMap(accountHandler::createAccount)
                .flatMap(accountResponseDTO -> ServerResponse
                        .status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(accountResponseDTO));
    }

    public Mono<ServerResponse> getAllAccounts(ServerRequest request){

        return accountHandler.getAllAccounts()
                .collectList()
                .flatMap(accountList ->
                        accountList.isEmpty() ?
                                ServerResponse.status(HttpStatus.NOT_FOUND).build()
                                : ServerResponse.ok().body(Flux.fromIterable(accountList), AccountResponseDTO.class)
                );
    }

}