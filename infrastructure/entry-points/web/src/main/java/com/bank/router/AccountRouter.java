package com.bank.router;

import com.bank.data.AccountRequestDTO;
import com.bank.data.AccountResponseDTO;
import com.bank.handler.AccountHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
@Tag(name = "Account", description = "Account endpoints.")
public class AccountRouter {
    private final AccountHandler accountHandler;

    public AccountRouter(AccountHandler accountHandler) {
        this.accountHandler = accountHandler;
    }

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = "/accounts",
                    method = RequestMethod.GET,
                    operation = @Operation(summary = "Get Accounts", description = "List all bank accounts.",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200", description = "Successfully obtained all accounts.",
                                            content = @Content(mediaType = "application/json", schema = @Schema())
                                    ),
                                    @ApiResponse(
                                            responseCode = "204", description = "No accounts to get.",
                                            content = @Content(mediaType = "application/json")
                                    ),
                                    @ApiResponse(
                                            responseCode = "400", description = "Bad request. Validation error. Missing fields?",
                                            content = @Content(mediaType = "application/json")
                                    )
                            }
                    )),
            @RouterOperation(
                    path = "accounts/{id}",
                    method = RequestMethod.GET,
                    operation = @Operation(summary = "Get account by id", description = "Find a single bank account by its id.",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200", description = "Successfully obtained account.",
                                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AccountResponseDTO.class))
                                    ),
                                    @ApiResponse(
                                            responseCode = "404", description = "Account not found.",
                                            content = @Content(mediaType = "application/json")
                                    ),
                                    @ApiResponse(
                                            responseCode = "400", description = "Bad request. Validation error. Missing fields?",
                                            content = @Content(mediaType = "application/json")
                                    )
                            })
            ),
            @RouterOperation(
                    path = "/accounts",
                    method = RequestMethod.POST,
                    operation = @Operation(summary = "Create new account.", description = "Create a new bank account.",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "201", description = "Successfully created account.",
                                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AccountResponseDTO.class))
                                    ),
                                    @ApiResponse(
                                            responseCode = "400", description = "Bad request. Validation error. Missing fields?",
                                            content = @Content(mediaType = "application/json")
                                    )
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> accountRoutes() {
        return RouterFunctions
                .route(GET("/accounts"), this::getAllAccounts)
                .andRoute(GET("accounts/{id}"), this::getAccountById)
                .andRoute(POST("/accounts").and(accept(MediaType.APPLICATION_JSON)), this::createAccount);
    }


    public Mono<ServerResponse> getAllAccounts(ServerRequest request) {
        return accountHandler.findAllAccounts()
                .collectList()
                .flatMap(accs -> ServerResponse
                        .status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(accs));
    }

    public Mono<ServerResponse> getAccountById(ServerRequest request) {
        String accId = request.pathVariable("id");
        return accountHandler.findAccountById(accId).flatMap(accountResponseDTO -> ServerResponse
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(accountResponseDTO));
    }

    public Mono<ServerResponse> createAccount(ServerRequest request) {
        return request.bodyToMono(AccountRequestDTO.class)
                .flatMap(accountHandler::createAccount)
                .flatMap(accountResponseDTO -> ServerResponse
                        .status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(accountResponseDTO));
    }
}
